package com.ivan.naves.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorConstants {
    ERROR_USER_ALREADY_EXISTS("El usuario ya está registrado", 1),
    ERROR_USER_NOT_FOUND("El usuario no existe en el sistema", 2),
    ;

    private String message;
    private int code;
}
