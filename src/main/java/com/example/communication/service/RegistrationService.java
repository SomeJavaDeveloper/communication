package com.example.communication.service;

import com.example.communication.controller.ControllerUtils;
import com.example.communication.model.Role;
import com.example.communication.model.User;
import com.example.communication.repository.UserRepository;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistrationService {

//  private final MailSender mailSender;

  private final UserRepository repository;

  public RegistrationService(
    //MailSender mailSender,
      UserRepository repository) {
    //this.mailSender = mailSender;
    this.repository = repository;
  }

  public boolean addUser(User user, MultipartFile file, PasswordEncoder encoder) throws IOException {
    User userFromDb = repository.findByUsername(user.getUsername());

    if (userFromDb != null) {
      return false;
    }

    user.setActive(false);
    user.setRoles(Collections.singleton(Role.USER));
    user.setPassword(encoder.encode(user.getPassword()));
//    user.setActivationCode(UUID.randomUUID().toString());
    ControllerUtils.saveMessage(file, user);

    user.setActive(true);

//    if (!StringUtils.isEmpty(user.getEmail())) {
//      String message = String.format(
//          "Hello, %s! \n" +
//              "Welcome to Sweater. Please, visit next link: https://communication-network.herokuapp.com/activate/%s",
//          user.getUsername(),
//          user.getActivationCode()
//      );

//      mailSender.send(user.getEmail(), "Activation code", message);
//    }

    return true;
  }

//  public boolean activateUser(String code) {
//    User user = repository.findByActivationCode(code);
//
//    if (user == null) {
//      return false;
//    }
//
//    user.setActivationCode(null);
//    user.setActive(true);
//
//    repository.save(user);
//
//    return true;
//  }
}
