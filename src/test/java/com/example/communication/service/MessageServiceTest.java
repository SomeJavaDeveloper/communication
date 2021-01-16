package com.example.communication.service;

import static com.example.communication.data.MessageTestData.ADMIN_MESSAGE;
import static com.example.communication.data.MessageTestData.MESSAGES;
import static com.example.communication.data.UserTestData.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.dto.MessageDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = {"/user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/comments_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/comments_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/message_likes_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/message_likes_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MessageServiceTest extends AbstractSpringTest {

  @Test
  void getAllMessagesTest() {
    List<MessageDTO> messages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(messages.size(), 2);
    assertEquals(messages, MESSAGES);

    messages = messageService.getAllMessagesTest("Admin message", ADMIN);
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), ADMIN_MESSAGE);
  }
}