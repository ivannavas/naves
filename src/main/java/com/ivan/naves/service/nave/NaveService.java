package com.ivan.naves.service.nave;

import com.ivan.naves.model.common.NotFoundServiceException;
import com.ivan.naves.model.nave.Nave;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NaveService {
    Page<Nave> findAllPaginated(Integer selectedPage, Integer maxRecordsPerPage);

    Nave save(Nave nave);

    Nave update(Long id, Nave nave);

    Nave delete(Long id);

    /**
     * Busca una nave por id
     *
     * @param id id de la nave
     * @return {@link Nave}
     * @throws NotFoundServiceException en caso de que no se encuentre ninguna nave con el id solicitado
     */
    Nave findOne(Long id);

    List<Nave> findByNombre(String nombre);
}
