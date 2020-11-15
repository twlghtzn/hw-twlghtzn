package com.greenfoxacademy.homeworktwlghtzn.users;

import com.greenfoxacademy.homeworktwlghtzn.dtos.LoginRequest;
import com.greenfoxacademy.homeworktwlghtzn.dtos.LoginResponse;
import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.CredentialsNotValidException;
import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.RequestIncorrectException;
import com.greenfoxacademy.homeworktwlghtzn.security.JwtUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final UserRepository userRepository;

  @Autowired
  public UserService(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.userRepository = userRepository;
  }

  public String createVerificationToken(String username, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtUtils.generateJwtToken(authentication);
  }

  public LoginResponse logUserIn(LoginRequest loginRequest) {
    String message = "";
    String username = "";
    String password = "";
    boolean usernameMissing = isUsernameMissing(loginRequest);
    boolean passwordMissing = isPasswordMissing(loginRequest);
    if (usernameMissing || passwordMissing) {
      message = composeMessageForMissingFields(usernameMissing, passwordMissing);
      throw new RequestIncorrectException(message);
    } else {
      username = loginRequest.getUsername();
      password = loginRequest.getPassword();
      if (!isUsernameInDB(username)) {
        message = composeMessageForInvalidCredentials(false, false);
        throw new CredentialsNotValidException(message);
      } else if (!isPasswordCorrect(username, password)) {
        message = composeMessageForInvalidCredentials(true, false);
        throw new CredentialsNotValidException(message);
      }
    }
    String jwt = createVerificationToken(username, password);
    return new LoginResponse(jwt, 200);
  }

  public boolean isUsernameMissing(LoginRequest loginRequest) {
    return loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty();
  }

  public boolean isPasswordMissing(LoginRequest loginRequest) {
    return loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty();
  }

  public String composeMessageForMissingFields(boolean usernameMissing, boolean passwordMissing) {
    StringBuilder message = new StringBuilder();
    if (usernameMissing || passwordMissing) {
      message.append("Missing parameter(s): ");
      if (usernameMissing) {
        message.append("username, ");
      }
      if (passwordMissing) {
        message.append("password, ");
      }
      message.delete(message.length() - 2, message.length());
    }
    return message.toString();
  }

  public String composeMessageForInvalidCredentials(boolean usernameFound,
                                                    boolean passwordCorrect) {
    StringBuilder message = new StringBuilder();
    if (!usernameFound) {
      message.append("Username incorrect");
    } else if (!passwordCorrect) {
      message.append("Password incorrect");
    }
    return message.toString();
  }


  public boolean isUsernameInDB(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean isPasswordCorrect(String username, String password) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.filter(value -> BCrypt.checkpw(password, value.getPassword())).isPresent();
  }
}
