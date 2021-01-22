package me.buildup.foodrecommendation.advice;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.advice.exception.LoginFailException;
import me.buildup.foodrecommendation.advice.exception.UserNotFoundException;
import me.buildup.foodrecommendation.dto.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse userNotFound(UserNotFoundException e) {
        return new ExceptionResponse(false, -1001, e.getMessage());
    }

    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse emailNotFound(LoginFailException e) {
        return new ExceptionResponse(false, -1002, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse global(Exception e) {
        return new ExceptionResponse(false, -1002, e.getMessage());
    }
}
