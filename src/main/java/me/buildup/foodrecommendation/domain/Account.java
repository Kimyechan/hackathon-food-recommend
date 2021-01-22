package me.buildup.foodrecommendation.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode @ToString
public class Account {
    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String userName;
}