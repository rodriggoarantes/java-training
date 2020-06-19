package com.training.cardgame;

import com.training.cardgame.infra.SavedTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

@Configuration
public class EventsJdbcListener {
    private static final Logger log = LoggerFactory.getLogger(EventsJdbcListener.class);

    @Bean
    public ApplicationListener<BeforeSaveEvent> timeStampingSaveTime() {
        return event -> {
            log.info("BeforeSaveEvent :: Called");
            final Object entity = event.getEntity();
            if (entity instanceof SavedTime) {
                log.info("BeforeSaveEvent :: Marked");
                ((SavedTime) entity).time();
            }
        };
    }
}