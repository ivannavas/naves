package com.ivan.naves.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.ivan.naves.controller.nave.NaveRestController.findOne(..)) && args(id)")
    public void findOneNave(Long id) {}

    /**
     * Saca por consola un log en caso de que se intente buscar una nave con id negativo
     *
     * @param id de la nave solicitada
     */
    @Before(value = "findOneNave(id)", argNames = "id")
    public void logNaveNegativeId(Long id) {
        if (id != null && id < 0) {
            log.warn("Se trató de obtener una nave con id negativo: {}", id);
        }
    }
}
