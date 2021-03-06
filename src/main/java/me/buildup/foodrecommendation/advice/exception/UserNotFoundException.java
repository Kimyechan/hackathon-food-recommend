package me.buildup.foodrecommendation.advice.exception;

public class UserNotFoundException extends RuntimeException{
    private final static String MESSAGE = "해당 유저가 존재하지 않습니다";
    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
