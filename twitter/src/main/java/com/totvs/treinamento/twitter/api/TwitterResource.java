package com.totvs.treinamento.twitter.api;

import com.totvs.treinamento.twitter.application.LikeService;
import com.totvs.treinamento.twitter.application.TwitterService;
import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import com.totvs.treinamento.twitter.domain.twitter.TwitterRepository;
import com.totvs.treinamento.twitter.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value={"tweets", "twitters", "posts"})
public class TwitterResource {

    @Autowired
    private TwitterService service;

    @Autowired
    private LikeService likeService;

    @GetMapping
    public Iterable<Twitter> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Twitter get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Twitter insert(@RequestBody Twitter param) {
        return service.save(param);
    }

    @GetMapping("/users/{id}")
    public Iterable<Twitter> search(@PathVariable Long id) {
        return service.findByUser(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteMy(@PathVariable Long id, @RequestHeader("user_id") Long userId) {
        service.deleteByOwner(id, userId);
        return Collections.singletonMap("msg", "Twitter deletado com sucesso");
    }

    @PostMapping("/{id}/likes")
    public Map<String, Boolean> likeTwitter(@PathVariable Long id, @RequestHeader("user_id") Long userId) {
        likeService.like(id, userId);
        return Collections.singletonMap("liked", true);
    }

    @DeleteMapping("/{id}/likes")
    public Map<String, Boolean> unlikeTwitter(@PathVariable Long id, @RequestHeader("user_id") Long userId) {
        likeService.unlike(id, userId);
        return Collections.singletonMap("liked", false);
    }

    @PatchMapping("/{id}/likes")
    public Map<String, Boolean> toggleLikeTwitter(@PathVariable Long id, @RequestHeader("user_id") Long userId) {
        final boolean status = likeService.toggleLike(id, userId);
        return Collections.singletonMap("liked", status);
    }
}
