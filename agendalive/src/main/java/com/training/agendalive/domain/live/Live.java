package com.training.agendalive.domain.live;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.agendalive.domain.video.Video;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "live")
public class Live {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime data;

    @Embedded
    private Video video;

    @UpdateTimestamp
    private LocalDateTime saved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getSaved() {
        return saved;
    }

    public void setSaved(LocalDateTime saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "Live{" +
                "id=" + id +
                '}';
    }
}
