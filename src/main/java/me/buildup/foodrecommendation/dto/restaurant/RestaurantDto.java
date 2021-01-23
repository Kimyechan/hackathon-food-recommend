package me.buildup.foodrecommendation.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.buildup.foodrecommendation.domain.FoodKind;
import me.buildup.foodrecommendation.domain.Location;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private Long id;

    private Location location;

    private FoodKind foodKind;

    private String name;

    @Min(1) @Max(5)
    private Integer myGrade;

    @Min(1) @Max(5)
    private Double avgUserGrade;

    private String description;
}
