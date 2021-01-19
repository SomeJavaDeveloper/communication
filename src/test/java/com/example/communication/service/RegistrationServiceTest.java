package com.example.communication.service;

import static com.example.communication.data.UserTestData.getNew;
import static com.example.communication.data.UserTestData.multipartFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.communication.AbstractSpringTest;
import com.example.communication.model.User;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = {"/user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegistrationServiceTest extends AbstractSpringTest {

  private final PasswordEncoder encoder;

  RegistrationServiceTest(PasswordEncoder encoder) {
    this.encoder = encoder;
  }

  @Test
  void addUser() throws IOException {
    List<User> actualUsers = userService.getAllUsersTest();
    assertEquals(3, actualUsers.size());
    registrationService.addUser(getNew(), multipartFile, encoder);
    assertEquals(4, actualUsers.size());
  }

  @Test
  void activateUser() {
//    registrationService.activateUser()
  }
}