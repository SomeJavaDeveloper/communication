package com.example.communication.tests.data;

import static com.example.communication.tests.data.CommentTestData.ADMIN_COMMENT;
import static com.example.communication.tests.data.CommentTestData.USER_COMMENT;
import static com.example.communication.tests.data.UserTestData.ADMIN;
import static com.example.communication.tests.data.UserTestData.USER;

import com.example.communication.model.Comment;
import com.example.communication.model.Message;
import com.example.communication.model.User;
import com.example.communication.model.dto.MessageDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MessageTestData {
  private final static Set<Comment> ADM_MESS_COMMENTS = new HashSet<>();

  public static final MessageDTO ADMIN_MESSAGE = new MessageDTO
      (1003L, "Admin message", ADMIN, "admin_test_message.png",
          LocalDateTime.of(2020, Month.of(12), 12, 14, 1), 1L, ADM_MESS_COMMENTS);

  public final static Set<User> ADMIN_LIKES = new HashSet<User>(){
    {
      add(USER);
    }
  };

  private final static Set<Comment> USR_MESS_COMMENTS = new HashSet<>();

  public static final MessageDTO USER_MESSAGE = new MessageDTO
      (1004L, "User message", USER, "user_test_message.png",
          LocalDateTime.of(2020, Month.of(12), 12, 14, 3), 2L, USR_MESS_COMMENTS);

  public final static Set<User> USER_LIKES = new HashSet<User>(){
    {
      add(USER); add(ADMIN);
    }
  };

  public static MultipartFile multipartFile;


  static {
    ADM_MESS_COMMENTS.add(ADMIN_COMMENT);
    USR_MESS_COMMENTS.add(USER_COMMENT);

    {
      byte[] content;
      try {
        content = Files.readAllBytes(Paths.get("/home/friday58/IdeaProjects/communication/src/test/resources/jaba.jpg"));
        multipartFile = new MockMultipartFile("jaba.jpg", "jaba.jpg", "image/jpeg", content);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static final List<MessageDTO> MESSAGES = Arrays.asList(ADMIN_MESSAGE, USER_MESSAGE);
  public static final MessageDTO[] MESSAGE_ARRAY = {ADMIN_MESSAGE, USER_MESSAGE};

  public static Message getNew() {
    return new Message("Admin message 2", "",
            LocalDateTime.of(2020, Month.of(12), 12, 14, 5), ADMIN, USR_MESS_COMMENTS);
  }
}
