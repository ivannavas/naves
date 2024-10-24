package com.ivan.naves.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {
    private final Integer code;

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(ErrorConstants error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    public Integer getHttpCode() {
        return 500;
    }
}
