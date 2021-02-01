package com.example.communication.tests.service;

import static com.example.communication.tests.data.MessageTestData.MESSAGES;
import static com.example.communication.tests.data.MessageTestData.multipartFile;
import static com.example.communication.tests.data.UserTestData.ADMIN;
import static com.example.communication.tests.data.UserTestData.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.User;
import com.example.communication.model.dto.MessageDTO;
import java.io.IOException;
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
    assertEquals(2, actualMessages.size());
    assertEquals(MESSAGES, actualMessages);

    profileService.addMessage(ADMIN, "Brand new message!", multipartFile);
    actualMessages = messageService.getAllMessagesTest("", ADMIN);
    assertEquals(3, actualMessages.size());

    List<MessageDTO> newMessage = messageService.getAllMessagesTest("Brand new message!", ADMIN);
    assertFalse(newMessage.isEmpty());
    assertTrue(newMessage.get(0).getFilename().contains("jaba.jpg"));
  }

  @Test
  void getUserById() {
    User user = profileService.getUserById(1001L);
    assertEquals(ADMIN, user);
  }

  @Test
  void subscribe() {
    User userDB = userRepository.findById(1002L).get();
    assertEquals(0, userDB.getSubscribers().size());

    profileService.subscribe(1002L, ADMIN);
    userDB = userRepository.findById(1002L).get();
    assertEquals(1, userDB.getSubscribers().size());
    assertTrue(userDB.getSubscribers().contains(ADMIN));
  }

  @Test
  void unsubscribe() {
    User userDB = userRepository.findById(1001L).get();
    assertEquals(1, userDB.getSubscribers().size());
    assertTrue(userDB.getSubscribers().contains(USER));

    profileService.unsubscribe(1001L, USER);
    userDB = userRepository.findById(1001L).get();
    assertEquals(0, userDB.getSubscribers().size());
  }
}