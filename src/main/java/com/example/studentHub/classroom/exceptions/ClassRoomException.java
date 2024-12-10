package com.example.studentHub.classroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Class exist")  // 404
public class ClassRoomException extends RuntimeException {
    public ClassRoomException(String message) {
        super(message);
    }
}
