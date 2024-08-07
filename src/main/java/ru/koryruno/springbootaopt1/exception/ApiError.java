package ru.koryruno.springbootaopt1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiError {

    private ApiStatus status;
    private String reason;
    private String message;
    private String timestamp;

}
