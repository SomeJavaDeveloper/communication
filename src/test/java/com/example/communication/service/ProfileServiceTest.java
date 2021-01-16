package com.example.communication.service;

import static com.example.communication.data.MessageTestData.MESSAGES;
import static com.example.communication.data.MessageTestData.multipartFile;
import static com.example.communication.data.UserTestData.ADMIN;
import static com.example.communication.data.UserTestData.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.User;
import com.example.communication.model.dto.MessageDTO;
import java.io.IOException;
import java.sql.SQLException;
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
@Sql(value = {"/user_subscriptions_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/user_subscriptions_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProfileServiceTest extends AbstractSpringTest {

  @Test
  void addMessage() throws IOException {
    List<MessageDTO> actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(actualMessages.size(), 2);
    assertEquals(actualMessages, MESSAGES);

    profileService.addMessage(ADMIN, "Brand new message!", multipartFile);
    actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(actualMessages.size(), 3);

    List<MessageDTO> newMessage = messageService.getAllMessagesTest("Brand new message!", ADMIN);
    assertFalse(newMessage.isEmpty());
    assertTrue(newMessage.get(0).getFilename().contains("jaba.jpg"));
  }

  @Test
  void getUserById() {
    User user = profileService.getUserById(1001L);
    assertEquals(user, ADMIN);
  }

  @Test
  void subscribe() {
    User userDB = userRepository.findById(1002L).get();
    assertEquals(userDB.getSubscribers().size(), 0);

    profileService.subscribe(1002L, ADMIN);
    userDB = userRepository.findById(1002L).get();
    assertEquals(userDB.getSubscribers().size(), 1);
    assertTrue(userDB.getSubscribers().contains(ADMIN));
  }

  @Test
  void unsubscribe() {
    User userDB = userRepository.findById(1001L).get();
    assertEquals(userDB.getSubscribers().size(), 1);
    assertTrue(userDB.getSubscribers().contains(USER));

    profileService.unsubscribe(1001L, USER);
    userDB = userRepository.findById(1001L).get();
    assertEquals(userDB.getSubscribers().size(), 0);
  }

  @Test
  void repost() throws SQLException {
//    String redirectLink = profileService.repost(
//        1004L, ADMIN, new RedirectAttributesModelMap(), "http://localhost:8080/profile/1004");
//    assertEquals(redirectLink, "redirect:/messages/" + 1004 + "/like");
  }
}