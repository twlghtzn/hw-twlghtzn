package com.greenfoxacademy.homeworktwlghtzn.bids;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.homeworktwlghtzn.bids.dtos.BidRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemRequest;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import com.greenfoxacademy.homeworktwlghtzn.users.UserRepository;
import com.greenfoxacademy.homeworktwlghtzn.users.dtos.LoginRequest;
import com.greenfoxacademy.homeworktwlghtzn.users.dtos.LoginResponse;
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
public class BidControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType()
      , MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

  //region setup

  public void addUser() {
    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);
  }

  //endregion

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenBidWithoutAuthorization_expectStatusUnauthorized()
      throws Exception {

    mockMvc.perform(get("/bid"))
        .andExpect(status().isUnauthorized());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithWrongId_expectStatusIsBadRequestAndAdequateMessage()
      throws Exception {

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 30))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Item not found")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithItemNotSellable_expectStatusIsBadRequestAndAdequateMessage()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 30))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 30))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Item can't be bought")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithNotEnoughDollars_whenBid_expectStatusIsBadRequestAndAdequateMessage()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 200F,
                    300F))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 201))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("You don't have enough money to place the bid")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithBidLowerThanStartingPrice_expectStatusIsBadRequestAndAdequateMessage()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 19))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Bid is too low")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithBidLowerThanLastBid_expectStatusIsBadRequestAndAdequateMessage()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 25))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 24))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Bid is too low")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithBidHigherThanLastBidButLowerThanPurchasePrice_expectStatusIsOkAndAdequateItemDetails()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 25))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 26))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", aMapWithSize(6)))
        .andExpect(jsonPath("name", is("testItem1")))
        .andExpect(jsonPath("description", is("testDescription1")))
        .andExpect(jsonPath("photoUrl", is("https://testURL1")))
        .andExpect(jsonPath("startingPrice", is(20)))
        .andExpect(jsonPath("purchasePrice", is(30)))
        .andExpect(jsonPath("bids").isArray())
        .andExpect(jsonPath("bids", hasSize(2)))
        .andExpect(jsonPath("bids[0]", aMapWithSize(2)))
        .andExpect(jsonPath("bids[0].sum", is(25)))
        .andExpect(jsonPath("bids[0].username", is("testUser1")))
        .andExpect(jsonPath("bids[1]", aMapWithSize(2)))
        .andExpect(jsonPath("bids[1].sum", is(26)))
        .andExpect(jsonPath("bids[1].username", is("testUser1")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUserWithEnoughDollars_whenBidWithBidEqualToPurchasePrice_expectStatusIsOkAndAdequateItemDetails()
      throws Exception {

    //region setup

    addUser();

    ObjectMapper objectMapper = new ObjectMapper();

    String result =
        mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(
                objectMapper.writeValueAsString(
                    new LoginRequest("testUser1", "password1"))))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    LoginResponse loginResponse = objectMapper.readValue(result, LoginResponse.class);
    String token = loginResponse.getToken();

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new CreateItemRequest("testItem1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 30))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", aMapWithSize(7)))
        .andExpect(jsonPath("name", is("testItem1")))
        .andExpect(jsonPath("description", is("testDescription1")))
        .andExpect(jsonPath("photoUrl", is("https://testURL1")))
        .andExpect(jsonPath("startingPrice", is(20)))
        .andExpect(jsonPath("purchasePrice", is(30)))
        .andExpect(jsonPath("bids").isArray())
        .andExpect(jsonPath("bids", hasSize(1)))
        .andExpect(jsonPath("bids[0]", aMapWithSize(2)))
        .andExpect(jsonPath("bids[0].sum", is(30)))
        .andExpect(jsonPath("bids[0].username", is("testUser1")))
        .andExpect(jsonPath("buyer", is("testUser1")));
  }
}
