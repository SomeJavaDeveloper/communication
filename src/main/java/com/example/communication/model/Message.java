package com.example.communication.model;

import com.example.communication.model.dto.MessageDTO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "message")
public class Message implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String filename;

    private LocalDateTime postTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "message_likes",
        joinColumns = {@JoinColumn(name = "message_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "message_comments",
            joinColumns = {@JoinColumn(name = "message_id")}
    )
    private Set<Comment> comments = new HashSet<>();

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public Message(MessageDTO messageDTO, Set<User> likes) {
        id = messageDTO.getId();
        text = messageDTO.getText();
        filename = messageDTO.getFilename();
        postTime = messageDTO.getPostTime();
        user = messageDTO.getUser();
        comments = messageDTO.getComments();
        this.likes = likes;
    }

    public Message(Long id, String text, String filename, LocalDateTime postTime,
        User user, Set<Comment> comments) {
        this.id = id;
        this.text = text;
        this.filename = filename;
        this.postTime = postTime;
        this.user = user;
        this.comments = comments;
    }

    public Message(String text, String filename, LocalDateTime postTime,
        User user, Set<Comment> comments) {
        this.text = text;
        this.filename = filename;
        this.postTime = postTime;
        this.user = user;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", filename='" + filename + '\'' +
            ", postTime=" + postTime +
            ", user=" + user +
            ", likes=" + likes +
            ", comments=" + comments +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return getId().equals(message.getId()) && Objects.equals(getText(), message.getText())
            && Objects.equals(getFilename(), message.getFilename()) && Objects
            .equals(getPostTime(), message.getPostTime()) && Objects
            .equals(getUser(), message.getUser()) && Objects
            .equals(getLikes(), message.getLikes()) && Objects
            .equals(getComments(), message.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

