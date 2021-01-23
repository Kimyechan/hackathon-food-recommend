package me.buildup.foodrecommendation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.buildup.foodrecommendation.config.JwtTokenProvider;
import me.buildup.foodrecommendation.domain.*;
import me.buildup.foodrecommendation.dto.restaurant.CreateReq;
import me.buildup.foodrecommendation.service.AccountService;
import me.buildup.foodrecommendation.service.RestaurantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestaurantControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private RestaurantService restaurantService;



    @Test
    @DisplayName("나의 지역 음식점 생성")
    public void create() throws Exception {
        Account account = createAccount();
        String accessToken = jwtProvider.createToken(String.valueOf(account.getId()), account.getRole());

        Location location = Location.builder()
                .location1("서울시")
                .location2("성동구")
                .location3("성수동")
                .build();

        CreateReq createReq = CreateReq.builder()
                .location(location)
                .foodKind(FoodKind.JAPANESE)
                .name("윤돈")
                .myGrade(5)
                .description("돈까스 맛집")
                .build();

        Restaurant restaurant = modelMapper.map(createReq, Restaurant.class);
        restaurant.setAccount(account);

        given(accountService.findAccountByEmail(account.getEmail())).willReturn(account);
        given(restaurantService.saveRestaurant(any())).willReturn(restaurant);

        mockMvc.perform(post("/api/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(createReq)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("해당 계정 도시 조회")
    public void getCityList() throws Exception {
        Account account = createAccount();

        String accessToken = jwtProvider.createToken(String.valueOf(account.getId()), account.getRole());
        given(accountService.findAccountByEmail(account.getEmail())).willReturn(account);
        List<String> cityList = new ArrayList<>();
        cityList.add("서울시");
        cityList.add("부산시");
        cityList.add("대구시");

        given(restaurantService.getCityList(account)).willReturn(cityList);
        mockMvc.perform(get("/api/restaurant/list/city")
                .header(HttpHeaders.AUTHORIZATION, accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public Account createAccount() {
        Account account = Account.builder()
                .id(1L)
                .email("abc@gmail.com")
                .password("1234")
                .userName("abc")
                .role(Role.USER)
                .build();

        given(accountService.loadUserByUsername(String.valueOf(account.getId())))
                .willReturn(new User(account.getEmail(), account.getPassword(), authorities(account.getRole())));

        return account;
    }

    private Collection<? extends GrantedAuthority> authorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}