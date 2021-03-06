package com.example.communication.tests.service;

//import static com.example.communication.tests.data.UserTestData.USER;
//import static com.example.communication.tests.data.UserTestData.multipartFile;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.example.communication.AbstractSpringTest;
//import com.example.communication.model.Role;
//import com.example.communication.model.User;
//import java.io.IOException;
//import java.util.EnumSet;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.jdbc.Sql;
//
//@Sql(value = {"/user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/user_subscriptions_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/user_subscriptions_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//@Sql(value = {"/user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//class EditServiceTest extends AbstractSpringTest {
//
//  @Test
//  void edit() throws IOException {
//    User actualUser = userRepository.findById(1002L).get();
//    assertEquals(USER, actualUser);
//    editService.edit(USER, "edited user", "new rl nm", "1995-11-01", "Penza", multipartFile);
//
//    actualUser = userRepository.findById(1002L).get();
//    User updatedUser = new User
//        (1002L, "edited user", "user123", true, "user123@gmail.com",
//            "jaba.jpg", "new rl nm",
//            "1995-11-01", "Penza", EnumSet.of(Role.USER));
//    assertEquals(updatedUser, actualUser);
//  }
//}