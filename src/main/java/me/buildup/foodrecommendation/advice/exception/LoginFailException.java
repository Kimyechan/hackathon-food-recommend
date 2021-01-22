package me.buildup.foodrecommendation.advice.exception;

public class LoginFailException extends RuntimeException {
    private static final String MESSAGE = "이메일이나 패스워드가 일치하지 않습니다";

    public LoginFailException() {
        super(MESSAGE);
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
