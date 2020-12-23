package com.example.communication.service;

import com.example.communication.controller.ControllerUtils;
import com.example.communication.model.Role;
import com.example.communication.model.User;
import com.example.communication.repository.UserRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {

  private final MailSender mailSender;

  private final UserRepository repository;

  public UserService(UserRepository repository, MailSender mailSender) {
    this.repository = repository;
    this.mailSender = mailSender;
  }

  public List<User> getAllUsers(String filter) {
    List<User> users;
    if (filter != null && !filter.isEmpty()) {
      users = repository.findAllByUsername(filter);
    } else {
      users = repository.findAll();
    }
    Collections.reverse(users);

    return users;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    UserDetails details = repository.findByUsername(s);
    if (details == null)
      details = new User();
    return details;
  }

  public void subscribe(User currentUser, User user) {
    user.getSubscribers().add(currentUser);

    repository.save(user);
  }

  public void unsubscribe(User currentUser, User user) {
    user.getSubscribers().remove(currentUser);

    repository.save(user);
  }

  public boolean addUser(User user, MultipartFile file, PasswordEncoder encoder) throws IOException {
    User userFromDb = repository.findByUsername(user.getUsername());

    if (userFromDb != null) {
      return false;
    }

    user.setActive(false);
    user.setRoles(Collections.singleton(Role.USER));
    user.setPassword(encoder.encode(user.getPassword()));
    user.setActivationCode(UUID.randomUUID().toString());
    ControllerUtils.saveMessage(file, user);

    if (!StringUtils.isEmpty(user.getEmail())) {
      String message = String.format(
          "Hello, %s! \n" +
              "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
          user.getUsername(),
          user.getActivationCode()
      );

      mailSender.send(user.getEmail(), "Activation code", message);
    }

    return true;
  }

  public boolean activateUser(String code) {
    User user = repository.findByActivationCode(code);

    if (user == null) {
      return false;
    }

    user.setActivationCode(null);
    user.setActive(true);

    repository.save(user);

    return true;
  }
}
