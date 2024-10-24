package com.ivan.naves.advice;

import com.ivan.naves.model.common.ErrorResponse;
import com.ivan.naves.model.common.ServiceException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Maneja las casuísticas de error 404 not found
     *
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @ExceptionHandler({NoResourceFoundException.class, ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleServiceException() {
        return new ResponseEntity<>(new ErrorResponse("El endpoint no existe", 404), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja los errores en la entrada (400 bad request)
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleServiceException(MethodArgumentNotValidException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja los errores del token JWT que producen que al usuario se le deniegue el acceso
     *
     * @param ex {@link JwtException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), 403), HttpStatus.FORBIDDEN);
    }

    /**
     * Maneja los errores controlados genéricos
     *
     * @param ex {@link ServiceException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), HttpStatusCode.valueOf(ex.getHttpCode()));
    }

    /**
     * Maneja el resto de los errores que se puedan producir
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServiceException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Se ha producido un error no controlado", 500), HttpStatusCode.valueOf(500));
    }
}
