package com.ivan.naves.service.usuario;

import com.ivan.naves.model.common.ErrorConstants;
import com.ivan.naves.model.common.ServiceException;
import com.ivan.naves.model.usuario.Login;
import com.ivan.naves.model.usuario.Usuario;
import com.ivan.naves.repository.usuario.UsuarioRepository;
import com.ivan.naves.service.GenericServiceTest;
import com.ivan.naves.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest extends GenericServiceTest {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private Usuario existentUsuario;
    private Usuario newUsuario;

    @BeforeEach
    void setUp() {
        existentUsuario = new Usuario();
        existentUsuario.setNombre("Existent");
        existentUsuario.setContrasena("existent123");

        newUsuario = new Usuario();
        newUsuario.setNombre("new");
        newUsuario.setContrasena("new123");
    }

    @Test
    void testSaveUser_OK() {
        Usuario savedUsuario = usuarioService.save(newUsuario);

        assertNotNull(savedUsuario);
    }

    @Test
    void testSaveUser_AlreadyExists() {
        Usuario existingUser = new Usuario();
        existingUser.setNombre(existentUsuario.getNombre());
        existingUser.setContrasena("pwd");

        ServiceException exception = assertThrows(ServiceException.class, () -> usuarioService.save(existentUsuario));
        assertEquals(ErrorConstants.ERROR_USER_ALREADY_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void testLogin_OK() {
        Login login = usuarioService.login(existentUsuario);

        assertNotNull(login);
    }

    @Test
    void testLogin_UserNotFound() {
        ServiceException exception = assertThrows(ServiceException.class, () -> usuarioService.login(newUsuario));
        assertEquals(ErrorConstants.ERROR_USER_NOT_FOUND.getCode(), exception.getCode());
    }
}
