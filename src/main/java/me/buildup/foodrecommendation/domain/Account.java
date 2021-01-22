package me.buildup.foodrecommendation.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode @ToString
public class Account {
    @Id @GeneratedValue
    private Long id;

    @Email
    private String email;

    private String password;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;
}