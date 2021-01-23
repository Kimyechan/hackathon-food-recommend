package me.buildup.foodrecommendation.repo;

import me.buildup.foodrecommendation.domain.Account;
import me.buildup.foodrecommendation.domain.FoodKind;
import me.buildup.foodrecommendation.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select distinct r.location.location1 from Restaurant r where r.account = :account")
    List<String> findCityListByAccount(@Param("account") Account account);

    @Query("select distinct r.location.location2 " +
            "from Restaurant r " +
            "where r.location.location1 = :city and r.account = :account")
    List<String> findGuListByCity(@Param("city") String city, @Param("account") Account account);

    @Query("select distinct r.location.location3 " +
            "from Restaurant r " +
            "where r.location.location1 = :city and r.location.location2 = :gu and r.account = :account")
    List<String> findDongListByAccount(@Param("city") String city, @Param("gu") String gu, @Param("account") Account account);

    @Query("select distinct r.foodKind " +
            "from Restaurant r " +
            "where r.location.location1 = :city and r.location.location2 = :gu and r.location.location3 = :dong and r.account = :account")
    List<String> findFoodList(@Param("city") String city,
                              @Param("gu") String gu,
                              @Param("dong") String dong,
                              @Param("account") Account account);

    @Query("select r.name " +
            "from Restaurant r " +
            "where r.location.location1 = :city and r.location.location2 = :gu and r.location.location3 = :dong and r.foodKind = :foodKind and r.account = :account")
    List<String> findRestaurantList(@Param("city") String city,
                                    @Param("gu") String gu,
                                    @Param("dong") String dong,
                                    @Param("foodKind") FoodKind foodKind,
                                    @Param("account") Account account);

    @Query("select r " +
            "from Restaurant r " +
            "where r.location.location1 = :city and r.location.location2 = :gu and r.location.location3 = :dong and r.foodKind = :foodKind and r.name = :restaurantName and r.account = :account")
    Restaurant findRestaurant(@Param("city") String city,
                                    @Param("gu") String gu,
                                    @Param("dong") String dong,
                                    @Param("foodKind") FoodKind foodKind,
                                    @Param("restaurantName") String restaurantName,
                                    @Param("account") Account account);
}
