package com.totvs.treinamento.twitter.api;

import com.totvs.treinamento.twitter.application.CommentService;
import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.comments.Comment;
import com.totvs.treinamento.twitter.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("comments")
public class CommentResource {

    @Autowired
    private CommentService service;

    @GetMapping
    public Iterable<Comment> list() {
        return service.list();
    }

    @GetMapping("/twitter/{id}")
    public Iterable<Comment> listByTwitter(@PathVariable Long id) {
        return service.findByTwitter(id);
    }

    @PostMapping
    public Comment save(@RequestBody Comment comment) {
        return service.save(comment);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteByOwner(@PathVariable Long id, @RequestHeader("user_id") Long userId) {
        service.deleteByOwner(id, userId);
        return Collections.singletonMap("msg", "Comentário deletado com sucesso");
    }
}
