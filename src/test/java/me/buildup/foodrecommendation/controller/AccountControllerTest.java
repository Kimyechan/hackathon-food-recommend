package me.buildup.foodrecommendation.controller;

import antlr.BaseAST;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.buildup.foodrecommendation.advice.exception.UserNotFoundException;
import me.buildup.foodrecommendation.config.JwtTokenProvider;
import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.Role;
import me.buildup.foodrecommendation.dto.account.JoinReq;
import me.buildup.foodrecommendation.dto.account.LoginReq;
import me.buildup.foodrecommendation.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AccountService accountService;

    @Test
    @DisplayName("회원가입 테스트")
    public void join() throws Exception {
        JoinReq joinReq = JoinReq.builder()
                .email("test@gmail.com")
                .password("1234")
                .userName("test")
                .build();

        Account account = Account.builder()
                .email(joinReq.getEmail())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .userName(joinReq.getUserName())
                .role(Role.USER)
                .build();

        given(accountService.saveAccount(any())).willReturn(account);

        mockMvc.perform(post("/api/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinReq)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value("test"));
    }

    @Test
    @DisplayName("로그인 테스트")
    public void login() throws Exception {
        LoginReq loginReq = LoginReq.builder()
                .email("test@gmail")
                .password("1234")
                .build();

        Account account = Account.builder()
                .email(loginReq.getEmail())
                .password(passwordEncoder.encode(loginReq.getPassword()))
                .userName("test")
                .role(Role.USER)
                .build();

        given(accountService.findAccountByEmail(loginReq.getEmail())).willReturn(account);

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReq)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists());
    }

    @Test
    @DisplayName("로그인 패스워드 불일치")
    public void loginPasswordNotMatch() throws Exception {
        LoginReq loginReq = LoginReq.builder()
                .email("test@gmail")
                .password("1234")
                .build();

        Account account = Account.builder()
                .email(loginReq.getEmail())
                .password(passwordEncoder.encode("123"))
                .userName("test")
                .role(Role.USER)
                .build();

        given(accountService.findAccountByEmail(loginReq.getEmail())).willReturn(account);

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 이메일 불일치")
    public void loginEmailNotMatch() throws Exception {
        LoginReq loginReq = LoginReq.builder()
                .email("test@gmail")
                .password("1234")
                .build();

        given(accountService.findAccountByEmail(loginReq.getEmail())).willThrow(UserNotFoundException.class);

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}