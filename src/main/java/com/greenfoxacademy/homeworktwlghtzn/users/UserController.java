package com.greenfoxacademy.homeworktwlghtzn.users;

import com.greenfoxacademy.homeworktwlghtzn.users.dtos.LoginRequest;
import com.greenfoxacademy.homeworktwlghtzn.users.dtos.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.logUserIn(loginRequest));
  }
}
