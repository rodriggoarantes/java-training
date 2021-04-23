package com.treinamento.infra;

import org.slf4j.LoggerFactory;

public interface Logger {
    org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    default org.slf4j.Logger logger() {
        return logger;
    }

    default void log(String msg) {
        this.logger().info(getClass().getSimpleName().concat(" : ").concat(msg));
    }
}
