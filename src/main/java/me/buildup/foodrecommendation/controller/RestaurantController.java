package me.buildup.foodrecommendation.controller;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.FoodKind;
import me.buildup.foodrecommendation.domain.Restaurant;
import me.buildup.foodrecommendation.dto.restaurant.*;
import me.buildup.foodrecommendation.service.AccountService;
import me.buildup.foodrecommendation.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/restaurant")
public class RestaurantController {
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final RestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity<CreateRes> create(Authentication authentication, @RequestBody CreateReq createReq) {
        Restaurant restaurant = modelMapper.map(createReq, Restaurant.class);
        Account account = accountService.findAccountByEmail(authentication.getName());
        restaurant.setAccount(account);
        restaurant.setAvgUserGrade(Double.valueOf(restaurant.getMyGrade()));

        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);

        CreateRes createRes = CreateRes.builder()
                .name(savedRestaurant.getName())
                .location(createReq.getLocation())
                .build();

        WebMvcLinkBuilder selfLinkBuilder = linkTo(methodOn(RestaurantController.class).create(authentication, createReq));
        URI createUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createUri).body(createRes);
    }

    @GetMapping("/list/city")
    public ResponseEntity<GetCityList> getCityList(Authentication authentication) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        List<String> cityList = restaurantService.getCityList(account);

        GetCityList getCityList = GetCityList.builder()
                .cityList(cityList)
                .build();

        return ResponseEntity.ok().body(getCityList);
    }

    @GetMapping("/list/gu")
    public ResponseEntity<GetGuList> getGuList(Authentication authentication, @RequestParam String city) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        List<String> guList = restaurantService.getGuList(city, account);

        GetGuList getGuList = GetGuList.builder()
                .guList(guList)
                .build();

        return ResponseEntity.ok().body(getGuList);
    }

    @GetMapping("/list/dong")
    public ResponseEntity<GetDongList> getGuList(Authentication authentication, @RequestParam String city, @RequestParam String gu) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        List<String> dongList = restaurantService.getDongList(city, gu, account);

        GetDongList getDongList = GetDongList.builder()
                .dongList(dongList)
                .build();

        return ResponseEntity.ok().body(getDongList);
    }

    @GetMapping("/list/kind")
    public ResponseEntity<GetFoodKind> getFoodKind(Authentication authentication,
                                                   @RequestParam String city,
                                                   @RequestParam String gu,
                                                   @RequestParam String dong) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        List<String> foodKindList = restaurantService.getFoodKindList(city, gu, dong, account);

        GetFoodKind foodKind = GetFoodKind.builder()
                .foodKindList(foodKindList)
                .build();

        return ResponseEntity.ok().body(foodKind);
    }

    @GetMapping("/list/name")
    public ResponseEntity getRestaurantName(Authentication authentication,
                                            @RequestParam String city,
                                            @RequestParam String gu,
                                            @RequestParam String dong,
                                            @RequestParam String foodKind) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        List<String> restaurantNameList = restaurantService.getRestaurantList(city, gu, dong, FoodKind.valueOf(foodKind), account);

        return ResponseEntity.ok().body(restaurantNameList);
    }

    @GetMapping("/")
    public ResponseEntity<RestaurantDto> getRestaurant(Authentication authentication,
                                        @RequestParam String city,
                                        @RequestParam String gu,
                                        @RequestParam String dong,
                                        @RequestParam String foodKind,
                                        @RequestParam String restaurantName) {
        Account account = accountService.findAccountByEmail(authentication.getName());
        Restaurant restaurant
                = restaurantService.getRestaurant(city, gu, dong, FoodKind.valueOf(foodKind), restaurantName, account);

        RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(restaurant.getId())
                .location(restaurant.getLocation())
                .foodKind(restaurant.getFoodKind())
                .name(restaurant.getName())
                .myGrade(restaurant.getMyGrade())
                .avgUserGrade(restaurant.getAvgUserGrade())
                .description(restaurant.getDescription())
                .build();

        return ResponseEntity.ok().body(restaurantDto);
    }

}
