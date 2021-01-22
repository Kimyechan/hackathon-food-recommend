package me.buildup.foodrecommendation.controller.web.dto;

import lombok.Getter;
import me.buildup.foodrecommendation.domain.Comment;
import me.buildup.foodrecommendation.domain.Restaurant;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
public class CommentListResponseDto {

    private Long id;
    private String userName;
    @Min(1) @Max(5)
    private Integer grade;
    private String content;
    private Restaurant restaurant;
//    private LocalDateTime modifiedDate;

    public CommentListResponseDto(Comment entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.grade = entity.getGrade();
        this.content = entity.getContent();
        this.restaurant = entity.getRestaurant();
//        this.modifiedDate = entity.getModifiedDate();
    }
}
