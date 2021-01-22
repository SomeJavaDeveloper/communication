package com.example.communication.repository;

import com.example.communication.model.chat.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select distinct cr from ChatRoom cr " +
            "where (cr.first = :firstUser and cr.second = :secondUser) " +
            "or (cr.second = :firstUser and cr.first = :secondUser) ")
    Optional<ChatRoom> findByFirstOrSecond(String firstUser, String secondUser);
}
