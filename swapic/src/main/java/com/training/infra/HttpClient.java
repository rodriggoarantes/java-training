package com.training.infra;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Objects;
import java.util.Optional;

public class HttpClient<T> {

    private static final String URLBASE = "https://swapi.dev/api";

    private final Class<T> genericClass;
    private final Gson parser;
    private OkHttpClient client;

    public HttpClient(Class<T> entity) {
        this.parser = new Gson();
        this.genericClass = entity;
    }

    public Optional<T> get(String path) {
        final Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(url(path))
                .build();

        final Call call = client().newCall(request);

        try (final Response response = call.execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                final String json = response.body().string();
                return Optional.of(parser.fromJson(json, genericClass));
            }
            return Optional.empty();
        } catch (Exception io) {
            return Optional.empty();
        }

    }

    private OkHttpClient client() {
        if (client == null) {
            this.client = new OkHttpClient();
        }
        return this.client;
    }

    private static String url(String path) {
        return URLBASE + "/" + path;
    }

}
