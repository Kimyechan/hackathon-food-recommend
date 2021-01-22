package me.buildup.foodrecommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.buildup.foodrecommendation.domain.Restaurant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String userName;
    private Integer grade;
    private String content;
    private Restaurant restaurant;
}
