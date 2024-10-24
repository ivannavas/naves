package com.ivan.naves.service.nave.impl;

import com.ivan.naves.model.common.NotFoundServiceException;
import com.ivan.naves.model.nave.Nave;
import com.ivan.naves.repository.nave.NaveRepository;
import com.ivan.naves.service.nave.NaveService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NaveServiceImpl implements NaveService {

    @Autowired
    private NaveRepository naveRepository;

    @Autowired
    private KafkaTemplate<String, Nave> kafkaTemplate;

    private final static String KAFKA_TOPIC = "naves";

    @Override
    public Page<Nave> findAllPaginated(Integer selectedPage, Integer maxRecordsPerPage) {
        return naveRepository.findAll(PageRequest.of(selectedPage, maxRecordsPerPage));
    }

    @Override
    public Nave save(Nave nave) {
        Nave savedNave = naveRepository.save(nave);
        kafkaTemplate.send(KAFKA_TOPIC, savedNave);
        return savedNave;
    }

    @Override
    public Nave update(Long id, Nave nave) {
        nave.setId(id);
        return naveRepository.save(nave);
    }

    @Cacheable("deleteNaveCache")
    @Override
    public Nave delete(Long id) {
        Nave nave = findOne(id);

        naveRepository.delete(nave);

        return nave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nave findOne(Long id) {
        return naveRepository.findById(id).orElseThrow(() -> new NotFoundServiceException("No existe ninguna nave con id " + id));
    }

    @Override
    public List<Nave> findByNombre(String nombre) {
        return naveRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
