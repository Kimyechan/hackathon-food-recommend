package me.buildup.foodrecommendation.service;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.FoodKind;
import me.buildup.foodrecommendation.domain.Restaurant;
import me.buildup.foodrecommendation.repo.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<String> getGuList(String city, Account account) {
        return restaurantRepository.findGuListByCity(city, account);
    }

    public List<String> getCityList(Account account) {
        return restaurantRepository.findCityListByAccount(account);
    }

    public List<String> getDongList(String city, String gu, Account account) {
        return restaurantRepository.findDongListByAccount(city, gu, account);
    }

    public List<String> getFoodKindList(String city, String gu, String dong, Account account) {
        return restaurantRepository.findFoodList(city, gu, dong, account);
    }

    public List<String> getRestaurantList(String city, String gu, String dong, FoodKind foodKind, Account account) {
        return restaurantRepository.findRestaurantList(city, gu, dong, foodKind, account);
    }

    public Restaurant getRestaurant(String city, String gu, String dong, FoodKind foodKind, String restaurantName, Account account) {
        return restaurantRepository.findRestaurant(city, gu, dong, foodKind, restaurantName, account);
    }
}
