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
public class Comment {
    @Id @GeneratedValue
    private Long id;

    private String userName;

    @Min(1) @Max(5)
    private Integer grade;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
