package com.training.application.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class HttpClient {

    private static final String URLBASE = "http://localhost:3000";

    private OkHttpClient client;

    public Optional<JsonElement> get(String path) {
        final Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(url(path))
                .build();

        final Call call = client().newCall(request);
        try (final Response response = call.execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                final String json = response.body().string();
                final JsonElement element = JsonParser.parseString(json);
                return Optional.of(element);
            }
            return Optional.empty();
        } catch (Exception io) {
            return Optional.empty();
        }
    }

    private OkHttpClient client() {
        if (client == null) {
            this.client =  new OkHttpClient();
        }
        return this.client;
    }

    private static String url(String path) {
        return URLBASE + "/" + path;
    }

}
