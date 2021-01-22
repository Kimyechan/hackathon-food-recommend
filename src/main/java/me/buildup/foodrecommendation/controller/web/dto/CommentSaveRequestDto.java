package me.buildup.foodrecommendation.controller.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.buildup.foodrecommendation.domain.Comment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String userName;
    @Min(1) @Max(5)
    private Integer grade;
    private String content;

    @Builder
    public CommentSaveRequestDto (String userName, Integer grade, String content) {
        this.userName = userName;
        this.content = content;
        this.grade = grade;
    }

    public Comment toEntity() {
        return Comment.builder()
                .userName(userName)
                .grade(grade)
                .content(content)
                .build();
    }


}
