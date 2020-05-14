package com.training.application.impl;

import com.google.gson.JsonElement;
import com.training.application.PostService;
import com.training.domain.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements PostService {

    private final HttpClient httpClient;

    public PostServiceImpl() {
        this.httpClient = new HttpClient();
    }

    public List<Post> listPosts() {
        Optional<JsonElement> json = this.httpClient.get("posts");
        if (json.isPresent()) {
            final List<Post> list = new ArrayList<>(0);
            for (JsonElement el : json.get().getAsJsonArray()) {
                final Post post = new Post(el.getAsJsonObject().get("title").getAsString(),
                        el.getAsJsonObject().get("author").getAsString());
                list.add(post);
            }
            return list;
        }
        return Collections.emptyList();
    }

}
