package me.buildup.foodrecommendation.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.buildup.foodrecommendation.domain.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRes {
    private String name;
    private Location location;
}
