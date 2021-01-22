package me.buildup.foodrecommendation.controller.web.dto;

import lombok.Getter;
import me.buildup.foodrecommendation.domain.Comment;
import me.buildup.foodrecommendation.domain.Restaurant;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class CommentResponseDto {

    private Long id;
    private String userName;
    private Integer grade;
    private String content;
    private Restaurant restaurant;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.grade = entity.getGrade();
        this.content = entity.getContent();
        this.restaurant = entity.getRestaurant();
    }
}


