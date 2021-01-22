package com.example.communication.repository;

import com.example.communication.model.Message;
import com.example.communication.model.User;
import com.example.communication.model.dto.MessageDTO;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select new com.example.communication.model.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.text like %:filter% and m.user in :users " +
            "group by m order by m.postTime desc")
    Page<MessageDTO> findByTextContains(@Param("filter") String filter, @Param("user") User user,
                                        @Param("users") Set<User> users, Pageable pageable);

    @Query("select new com.example.communication.model.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.user in :users " +
            "group by m order by m.postTime desc")
    Page<MessageDTO> findAll(@Param("user") User user, @Param("users") Set<User> users, Pageable pageable);

    @Query("select new com.example.communication.model.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.user = :author or m.id in :repostArray " +
            "group by m order by m.postTime desc")
    Page<MessageDTO> findByUserId(@Param("user") User user, @Param("author") User author,
                                  Pageable pageable, @Param("repostArray") List<Long> repostArray);

    @Modifying
    @Query("delete from Message m where m.id = ?1")
    void deleteById(Long aLong);

    //Tests only
    @Query("select new com.example.communication.model.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "group by m order by m.id desc ")
    List<MessageDTO> findAllTest(@Param("user") User user);

    @Query("select new com.example.communication.model.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.text like %:filter% " +
            "group by m order by m.id")
    Iterable<MessageDTO> findByTextContainsTest(@Param("filter") String filter, @Param("user") User user);
}
