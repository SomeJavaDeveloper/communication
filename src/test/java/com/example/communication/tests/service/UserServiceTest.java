package com.example.communication.tests.service;

import static com.example.communication.tests.data.UserTestData.ADMIN;
import static com.example.communication.tests.data.UserTestData.USER;
import static com.example.communication.tests.data.UserTestData.USERS;
import static com.example.communication.tests.data.UserTestData.USR;
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
    assertEquals(Arrays.asList(USR, USER), actualUsers.getContent());
  }

  @Test
  void getAllUsersTest() {
    List<User> actualUsers = userService.getAllUsersTest();
    assertEquals(USERS, actualUsers);
  }

  @Test
  void editUserInUserList() {
//    userService.editUserInUserList();
  }

  @Test
  void deleteUserInUserList() {
    List<User> actualUsers = userService.getAllUsersTest();
    assertEquals(USERS, actualUsers);

    userService.deleteUserInUserList(USER, ADMIN, PageRequest.of(0, 5));
    actualUsers = userService.getAllUsersTest();
    assertEquals(2, actualUsers.size());
    assertTrue(actualUsers.contains(ADMIN));
  }
}