package me.buildup.foodrecommendation.dto.exception;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Boolean success;
    private Integer code;
    private String message;
}