package com.example.communication.service;

import static com.example.communication.data.MessageTestData.USER_MESSAGE;
import static com.example.communication.data.MessageTestData.multipartFile;
import static com.example.communication.data.UserTestData.ADMIN;
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
    assertEquals(actualMessages.size(), 3);

    List<MessageDTO> newMessage = messageService.getAllMessagesTest("Brand new message!", ADMIN);
    assertFalse(newMessage.isEmpty());
    assertTrue(newMessage.get(0).getFilename().contains("jaba.jpg"));
  }

  @Test
  void delete() {
    mainService.delete(ADMIN, 1003L, new RedirectAttributesModelMap(), "http://localhost:8080/");
    List<MessageDTO> actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(actualMessages.size(), 1);
    assertEquals(actualMessages.get(0), USER_MESSAGE);
  }

//  @Test
//  void like() {
//    mainService.like(ADMIN, new Message(ADMIN_MESSAGE, ADMIN_LIKES), new RedirectAttributesModelMap(), "http://localhost:8080/");
//    Message m = messageRepository.findById(1003L).get();
//    assertEquals(m.getLikes().size(), 2);
//  }
//
//  @Test
//  void dislike() {
//  }
}