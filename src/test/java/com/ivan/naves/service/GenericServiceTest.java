package com.ivan.naves.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenericServiceTest {

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void setUpBase() {
        flyway.clean();
        flyway.migrate();
    }
}
