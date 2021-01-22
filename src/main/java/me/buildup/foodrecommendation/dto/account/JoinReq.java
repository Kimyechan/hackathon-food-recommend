package me.buildup.foodrecommendation.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinReq {
    @NotEmpty private String email;
    @NotEmpty private String password;
    @NotEmpty private String userName;
}
