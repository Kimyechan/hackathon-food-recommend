package me.buildup.foodrecommendation.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode @ToString
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Min(1) @Max(5)
    private Integer grade;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Builder
    public Comment(String userName, Integer grade, String content) {
        this.userName = userName;
        this.grade = grade;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }



}



