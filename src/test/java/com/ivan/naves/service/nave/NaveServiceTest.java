package com.ivan.naves.service.nave;

import com.ivan.naves.model.common.NotFoundServiceException;
import com.ivan.naves.model.nave.Nave;
import com.ivan.naves.service.GenericServiceTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "naves" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class NaveServiceTest extends GenericServiceTest {
    @Autowired
    private NaveService naveService;

    private Nave newNave;
    private Nave existentNave;

    @BeforeEach
    void setUp() {
        existentNave = new Nave();
        existentNave.setId(1L);
        existentNave.setNombre("Existent");
        existentNave.setNumeroMotores(2L);

        newNave = new Nave();
        newNave.setId(2L);
        newNave.setNombre("Nave");
        newNave.setNumeroMotores(3L);
    }

    @Test
    void testFindAllPaginated() {
        Page<Nave> result = naveService.findAllPaginated(0, 10);

        assertEquals(1, result.getContent().size());

        assertEquals(existentNave, result.getContent().getFirst());
    }

    @Test
    @DirtiesContext
    void testSave() {
        Nave savedNave = naveService.save(newNave);

        assertEquals(newNave, savedNave);
    }

    @Test
    @DirtiesContext
    void testSave_KafkaIntegration (@Autowired EmbeddedKafkaBroker embeddedKafkaBroker) {
        Consumer<String, Nave> consumer = null;

        try {
            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
            consumer = new KafkaConsumer<>(consumerProps, new org.apache.kafka.common.serialization.StringDeserializer(), new JsonDeserializer<>(Nave.class, false));
            embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "naves");

            naveService.save(newNave);

            Nave kafkaProducedNave = KafkaTestUtils.getSingleRecord(consumer, "naves").value();

            newNave.setId(null);
            assertEquals(newNave, kafkaProducedNave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }

    @Test
    void testUpdate() {
        Long id = existentNave.getId();
        Nave updatedNave = new Nave();
        updatedNave.setId(id);
        updatedNave.setNombre("Updated Name");
        updatedNave.setNumeroMotores(2L);

        Nave result = naveService.update(id, updatedNave);

        assertEquals(updatedNave, result);
    }

    @Test
    void testDelete_OK() {
        Long id = existentNave.getId();

        Nave deletedNave = naveService.delete(id);

        assertEquals(existentNave, deletedNave);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 3L;

        NotFoundServiceException exception = assertThrows(NotFoundServiceException.class, () -> {
            naveService.delete(id);
        });

        assertTrue(exception.getMessage().contains("No existe ninguna nave con id " + id));
    }

    @Test
    void testFindOne_OK() {
        Long id = existentNave.getId();

        Nave foundNave = naveService.findOne(id);

        assertEquals(existentNave, foundNave);
    }

    @Test
    void testFindOne_NotFound() {
        Long id = 3L;

        NotFoundServiceException exception = assertThrows(NotFoundServiceException.class, () -> {
            naveService.findOne(id);
        });

        assertTrue(exception.getMessage().contains("No existe ninguna nave con id " + id));
    }

    @Test
    void testFindByNombre() {
        List<Nave> result = naveService.findByNombre("existent");

        assertEquals(1, result.size());
        assertTrue(result.contains(existentNave));
    }
}
