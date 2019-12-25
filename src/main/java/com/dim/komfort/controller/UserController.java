package com.dim.komfort.controller;

import com.dim.komfort.domain.User;
import com.dim.komfort.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<String> testApp() {
    Optional<User> userOptional = userService.getById(1L);
    if (userOptional.isPresent())
      return ResponseEntity.ok("DB working perfect user: " + userOptional.get().getUserName());
    else
      return ResponseEntity.notFound().build();
  }
}
