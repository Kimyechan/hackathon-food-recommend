package me.buildup.foodrecommendation.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@Embeddable
public class Location {
    private String location1;
    private String location2;
    private String location3;
}
