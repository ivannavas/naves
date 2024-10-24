package com.ivan.naves.controller.nave;

import com.ivan.naves.model.nave.Nave;
import com.ivan.naves.service.nave.NaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/naves")
public class NaveRestController {

    @Autowired
    private NaveService naveService;

    @GetMapping
    public Page<Nave> findAll(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return naveService.findAllPaginated(pageNumber, pageSize);
    }

    @GetMapping("/by-nombre")
    public List<Nave> findByNombre(@RequestParam String nombre) {
        return naveService.findByNombre(nombre);
    }

    @PostMapping
    public Nave save(@Validated @RequestBody Nave nave) {
        return naveService.save(nave);
    }

    @PutMapping("/{id}")
    public Nave update(@PathVariable Long id, @Validated @RequestBody Nave nave) {
        return naveService.update(id, nave);
    }

    @GetMapping("/{id}")
    public Nave findOne(@PathVariable Long id) {
        return naveService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Nave delete(@PathVariable Long id) {
        return naveService.delete(id);
    }
}
