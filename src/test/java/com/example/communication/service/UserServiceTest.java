package com.example.communication.service;

import static com.example.communication.data.UserTestData.ADMIN;
import static com.example.communication.data.UserTestData.USER;
import static com.example.communication.data.UserTestData.USERS;
import static com.example.communication.data.UserTestData.USR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.User;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = {"/user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/user_subscriptions_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/user_subscriptions_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserServiceTest extends AbstractSpringTest {

  @Test
  void getAllUsers() {
    Page<User> actualUsers = userService.getAllUsers("admin", "", PageRequest.of(0, 5));
    assertEquals(actualUsers.getContent(), Arrays.asList(USR, USER));
  }

  @Test
  void getAllUsersTest() {
    List<User> actualUsers = userService.getAllUsersTest();
    assertEquals(actualUsers, USERS);
  }

  @Test
  void editUserInUserList() {
//    userService.editUserInUserList();
  }

  @Test
  void deleteUserInUserList() {
    List<User> actualUsers = userService.getAllUsersTest();
    assertEquals(actualUsers, USERS);

    userService.deleteUserInUserList(USER, ADMIN, PageRequest.of(0, 5));
    actualUsers = userService.getAllUsersTest();
    assertEquals(actualUsers.size(), 2);
    assertTrue(actualUsers.contains(ADMIN));
  }
}