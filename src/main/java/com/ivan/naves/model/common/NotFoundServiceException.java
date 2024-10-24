package com.ivan.naves.model.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundServiceException extends ServiceException {

    private static final Integer HTTP_CODE = 404;

    public NotFoundServiceException(String message) {
      super(message, HTTP_CODE);
    }

    @Override
    public Integer getHttpCode() {
        return HTTP_CODE;
    }
}
