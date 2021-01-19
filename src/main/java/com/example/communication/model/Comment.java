package com.example.communication.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String filename;

    private LocalDateTime postTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "message_comments",
            joinColumns = {@JoinColumn(name = "comments_id")}
    )
    private Message message;

    public Comment(String text, User user, Message message) {
        this.text = text;
        this.user = user;
        this.message = message;
    }

    public Comment(Long id, String text, String filename, LocalDateTime postTime,
        User user, Message message) {
        this.id = id;
        this.text = text;
        this.filename = filename;
        this.postTime = postTime;
        this.user = user;
        this.message = message;
    }

    public Comment(String text, String filename, LocalDateTime postTime,
        User user, Message message) {
        this.text = text;
        this.filename = filename;
        this.postTime = postTime;
        this.user = user;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", filename='" + filename + '\'' +
            ", postTime=" + postTime +
            ", user=" + user +
            ", message=" + message +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) o;
        return getId().equals(comment.getId()) && Objects.equals(getText(), comment.getText())
            && Objects.equals(getFilename(), comment.getFilename()) && Objects
            .equals(getPostTime(), comment.getPostTime()) && Objects
            .equals(getUser(), comment.getUser()) && Objects
            .equals(getMessage(), comment.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
