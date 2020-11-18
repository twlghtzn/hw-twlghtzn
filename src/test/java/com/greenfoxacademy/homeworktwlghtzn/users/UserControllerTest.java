package com.greenfoxacademy.homeworktwlghtzn.users;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.homeworktwlghtzn.users.dtos.LoginRequest;
import java.nio.charset.StandardCharsets;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType()
      , MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithMissingUsername_StatusIsBadRequestAndAdequateMessage()
      throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("", "password1"))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): username")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithMissingPassword_StatusIsBadRequestAndAdequateMessage()
      throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("testUser1", ""))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): password")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithMissingUsernameAndPassword_StatusIsBadRequestAndAdequateMessage()
      throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("", ""))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): username, password")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithMissingRequestBody_StatusIsBadRequestAndAdequateMessage()
      throws Exception {

    mockMvc.perform(post("/login")
        .contentType(contentType))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Required request body is missing")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithIncorrectUsername_StatusIsUnauthorizedAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("testUser2", "password1"))))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("message", is("Username incorrect")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithIncorrectPassword_StatusIsUnauthorizedAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("testUser1", "password2"))))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("message", is("Password incorrect")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserInDB_whenLoginWithValidCredentials_StatusIsOkAndTokenAndGBDAmountIsReturned()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    ObjectMapper objectMapper = new ObjectMapper();

    mockMvc.perform(post("/login")
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new LoginRequest("testUser1", "password1"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("token", isA(String.class)))
        .andExpect(jsonPath("tokenType", is("HW-token")))
        .andExpect(jsonPath("account", is(200)));
  }
}
