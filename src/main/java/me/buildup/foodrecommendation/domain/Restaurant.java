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
public class Restaurant {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private FoodKind foodKind;

    private String name;

    @Min(1) @Max(5)
    private Integer myGrade;

    @Min(1) @Max(5)
    private Double avgUserGrade;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
