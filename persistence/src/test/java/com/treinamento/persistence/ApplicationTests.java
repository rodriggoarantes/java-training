package com.treinamento.persistence;

import com.treinamento.persistence.config.ConfigIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTests extends ConfigIT {

    @Test
    void contextLoads() {
        Application.main(new String[] { "--spring.profiles.active=test" });
        Assertions.assertTrue(true);
    }
}
