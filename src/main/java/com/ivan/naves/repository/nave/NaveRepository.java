package com.ivan.naves.repository.nave;

import com.ivan.naves.model.nave.Nave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NaveRepository extends JpaRepository<Nave, Long> {
    List<Nave> findByNombreContainingIgnoreCase(String nombre);
}
