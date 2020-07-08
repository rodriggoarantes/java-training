package com.totvs.treinamento.twitter.domain.twitter;

import com.totvs.treinamento.twitter.domain.user.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "twitter")
public class Twitter {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDateTime date;

    private String country;

    @OneToOne
    private User user;

    // comments
    // likes

    public Twitter() {}

    public Twitter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
