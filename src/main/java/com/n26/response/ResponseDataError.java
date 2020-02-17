package com.n26.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseDataError {

    private final int status;
    private final String message;
}