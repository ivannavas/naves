package com.ivan.naves.service.usuario.impl;

import com.ivan.naves.model.common.ErrorConstants;
import com.ivan.naves.model.common.ServiceException;
import com.ivan.naves.model.usuario.Login;
import com.ivan.naves.model.usuario.Usuario;
import com.ivan.naves.repository.usuario.UsuarioRepository;
import com.ivan.naves.service.jwt.JwtService;
import com.ivan.naves.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Usuario save(Usuario usuario) {
       if(usuarioRepository.findByNombre(usuario.getNombre()).isPresent()) {
            throw new ServiceException(ErrorConstants.ERROR_USER_ALREADY_EXISTS);
       }

        usuario.encodePassword(passwordEncoder);

        return usuarioRepository.save(usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Login login(Usuario usuario) {
        if(!validate(usuario)) {
            throw new ServiceException(ErrorConstants.ERROR_USER_NOT_FOUND);
        }

        return new Login(jwtService.generateToken(usuario.getUsername()));
    }

    /**
     * Valida un usuario
     *
     * @param usuario {@link Usuario}
     * @return true en caso de que el usuario sea válido
     */
    private Boolean validate(Usuario usuario) {
        Optional<Usuario> existentUsuario = usuarioRepository.findByNombre(usuario.getNombre());
        return existentUsuario.map(value -> passwordEncoder.matches(usuario.getContrasena(), value.getContrasena())).orElse(false);
    }
}
