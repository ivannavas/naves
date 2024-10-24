package com.ivan.naves.advice;

import com.ivan.naves.model.common.Page;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@NonNullApi
public class PaginationControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * Establece los casos en los que se ejecuta el advice
     *
     * @param returnType {@link MethodParameter} que contiene información sobre el tipo que se retorna en el controlador
     * @param converterType {@link Class}
     * @return true en caso de que el advice se tenga que ejecutar
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return org.springframework.data.domain.Page.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * Se ejecuta en los casos soportados antes de la escritura del body
     *
     * @param body el body que se va a escribir
     * @param returnType {@link MethodParameter} que contiene información sobre el tipo que se retorna en el controlador
     * @param selectedContentType {@link MediaType}
     * @param selectedConverterType {@link Class}
     * @param request the current request {@link ServerHttpRequest}
     * @param response the current response {@link ServerHttpResponse}
     * @return el body que se va a escribir tras los cambios hechos en el método
     */
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof org.springframework.data.domain.Page<?> page) {
            return new Page<>(page);
        }
        return body;
    }
}
