package com.ivan.naves.controller.usuario;

import com.ivan.naves.model.usuario.Login;
import com.ivan.naves.model.usuario.Usuario;
import com.ivan.naves.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario register(@Validated @RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PostMapping("/login")
    public Login login(@Validated @RequestBody Usuario usuario) {
        return usuarioService.login(usuario);
    }
}
