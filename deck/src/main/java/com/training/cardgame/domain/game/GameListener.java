package com.training.cardgame.domain.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GameListener extends AbstractRelationalEventListener<Game> {
    private static final Logger log = LoggerFactory.getLogger(GameListener.class);

    @Override
    protected void onBeforeSave(BeforeSaveEvent<Game> event) {
        log.info("onbeforesave");
        event.getEntity().setSaved(LocalDateTime.now());
        super.onBeforeSave(event);
    }
}
