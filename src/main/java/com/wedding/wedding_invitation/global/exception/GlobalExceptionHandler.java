package com.wedding.wedding_invitation.global.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 Controller에 전역예외처리 등록
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class) // 예외가 캐치되면 해당 메서드로 처리
    public ResponseEntity<String> IllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
