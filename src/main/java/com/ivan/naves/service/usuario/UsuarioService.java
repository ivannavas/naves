package com.ivan.naves.service.usuario;

import com.ivan.naves.model.common.ServiceException;
import com.ivan.naves.model.usuario.Login;
import com.ivan.naves.model.usuario.Usuario;

public interface UsuarioService {
    /**
     * Guarda un usuario en base de datos
     *
     * @param usuario {@link Usuario}
     * @return {@link Usuario}
     * @throws ServiceException en caso de que ya exista un usuario registrado con el mismo nombre
     */
    Usuario save(Usuario usuario);

    /**
     * Gestiona un login de usuario
     *
     * @param usuario {@link Usuario}
     * @return {@link Login}
     * @throws ServiceException en caso de que el usuario no sea válido
     */
    Login login(Usuario usuario);
}
