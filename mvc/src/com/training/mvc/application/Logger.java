package com.training.mvc.application;

public interface Logger {
    default void log(String msg) {
        System.out.println(getClass().getSimpleName().concat(" : ").concat(msg));
    }
}