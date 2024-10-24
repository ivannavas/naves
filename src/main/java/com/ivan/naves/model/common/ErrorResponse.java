package com.ivan.naves.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse(ServiceException ex) {
        this.message = ex.getMessage();
        this.code = ex.getCode();
    }
}
