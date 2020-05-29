package com.training.application.impl;

import com.training.application.ApiService;
import com.training.domain.Planet;
import com.training.infra.HttpClient;
import com.training.infra.Logger;

import java.util.Optional;

public class SwapiServiceImpl implements ApiService, Logger {

    private final HttpClient<Planet> planetClient;

    public SwapiServiceImpl() {
        this.planetClient = new HttpClient<>(Planet.class);
    }

    public Planet planet() {
        final Optional<Planet> planet = this.planetClient.get("planets/1/");
        if (planet.isPresent()) {
            log(String.format("Resultado: %s", planet.get().getName()));
            return planet.get();
        } else {
            throw new RuntimeException("Planeta nao encontrado");
        }
    }

}
