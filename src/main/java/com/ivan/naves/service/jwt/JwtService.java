package com.ivan.naves.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);

    /**
     * Devuelve el claim solicitado del token
     *
     * @param token token jwt
     * @param claim claim que se quiere obtener
     * @param clazz tipo del claim
     * @return el claim a obtener del tipo especificado
     * @param <T> tipo del claim que se va a obtener
     */
    <T> T extractClaim(String token, String claim, Class<T> clazz);

    Boolean isTokenValid(String token, UserDetails userDetails);
}
