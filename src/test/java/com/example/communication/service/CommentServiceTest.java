package com.example.communication.service;

import static com.example.communication.data.MessageTestData.multipartFile;
import static com.example.communication.data.UserTestData.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.Message;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Sql(value = {"/user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/comments_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/comments_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/message_likes_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/message_likes_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommentServiceTest extends AbstractSpringTest {

  @Test
  void addCommentTest() throws IOException {
    Message m = messageRepository.findById(1004L).get();
    assertEquals(m.getComments().size(), 1);

    commentService.addComment("Brand new comment!", multipartFile, ADMIN,
        1004L, new RedirectAttributesModelMap(), "http://localhost:8080/");
    messageRepository.findById(1004L).get();
    m = messageRepository.findById(1004L).get();
    assertEquals(m.getComments().size(), 2);
  }
}