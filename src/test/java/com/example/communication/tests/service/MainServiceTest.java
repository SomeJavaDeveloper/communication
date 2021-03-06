package com.example.communication.tests.service;

import static com.example.communication.tests.data.MessageTestData.USER_MESSAGE;
import static com.example.communication.tests.data.MessageTestData.multipartFile;
import static com.example.communication.tests.data.UserTestData.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.dto.MessageDTO;
import java.io.IOException;
import java.util.List;
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
class MainServiceTest extends AbstractSpringTest {

  @Test
  void addMessage() throws IOException {
    mainService.addMessage(ADMIN, "Brand new message!", multipartFile);
    List<MessageDTO> actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(3, actualMessages.size());

    List<MessageDTO> newMessage = messageService.getAllMessagesTest("Brand new message!", ADMIN);
    assertFalse(newMessage.isEmpty());
    assertTrue(newMessage.get(0).getFilename().contains("jaba.jpg"));
  }

  @Test
  void delete() {
    mainService.delete(ADMIN, 1003L, new RedirectAttributesModelMap(), "http://localhost:8080/");
    List<MessageDTO> actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(1, actualMessages.size());
    assertEquals(USER_MESSAGE, actualMessages.get(0));
  }
}