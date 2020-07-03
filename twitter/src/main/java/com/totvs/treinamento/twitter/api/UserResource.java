package com.totvs.treinamento.twitter.api;

import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public Iterable<User> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.find(id);
    }

    @PostMapping
    public User insert(@RequestBody User user) {
        return service.insert(user);
    }

    @GetMapping("/search")
    public User search(@RequestParam String login) {
        return service.findByLogin(login);
    }
}
