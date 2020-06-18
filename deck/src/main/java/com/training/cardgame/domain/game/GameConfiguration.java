package com.training.cardgame.domain.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

import java.time.LocalDateTime;

@Configuration
@EnableJdbcRepositories
public class GameConfiguration extends AbstractJdbcConfiguration {
    @Bean
    public BeforeSaveCallback<Game> timeStampingSaveTime() {
        return (entity, aggregateChange) -> {
            entity.setSaved(LocalDateTime.now());
            return entity;
        };
    }
}



