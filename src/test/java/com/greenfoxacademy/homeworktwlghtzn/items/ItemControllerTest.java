package com.greenfoxacademy.homeworktwlghtzn.items;

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
public class ItemControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemRepository itemRepository;

  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType()
      , MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

  //region create item

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithMissingName_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("", "testDescription", "https://testURL", 20F, 30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): name")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithMissingDescription_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testItem", "", "https://testURL", 20F, 30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): description")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithMissingPhotoURL_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "", 20F, 30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): photoUrl")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithMissingStartingPrice_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", null,
                    30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): starting price")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithMissingPurchasePrice_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 20F,
                    null))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Missing parameter(s): purchase price")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithAllFieldsMissing_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("", "", "", null, null))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message",
            is("Missing parameter(s): name, description, photoUrl, starting price, purchase price")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithRequestBodyMissing_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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

    mockMvc.perform(post("/items/create").header("Authorization", "HW-token " + token))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Required request body is missing")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithInvalidStartingPrice_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 20.5F,
                    30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message",
            is("Starting price and purchase price has to be a positive whole number")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithInvalidPurchasePrice_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 20F,
                    -30.5F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message",
            is("Starting price and purchase price has to be a positive whole number")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithStartingPriceTooLow_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 0F, 30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Starting price too low")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithPurchasePriceTooLow_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 20F, 19F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Purchase price is lower than starting price")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItemWithInvalidPhotoURL_statusIsBadRequestAndAdequateMessage()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "httpstestURL", 20F, 30F))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("photoURL must be a valid URL")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  public void givenAUser_whenCreateItem_statusIsOkAndItemDetailsReturned()
      throws Exception {

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName", "testDescription", "https://testURL", 20F, 30F))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name", is("testName")))
        .andExpect(jsonPath("description", is("testDescription")))
        .andExpect(jsonPath("photoUrl", is("https://testURL")))
        .andExpect(jsonPath("startingPrice", is(20)))
        .andExpect(jsonPath("purchasePrice", is(30)));
  }

  //endregion

  //region list items

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUser_whenItemsListWithoutParameter_statusIsOkAndFirst2SellableItemsWithAdequateDetailsReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    Item item1 = new Item(1L, "testItem1", "testDescription1", "https://testURL1", 20, 30, System.currentTimeMillis());
    itemRepository.save(item1);
    Item item2 = new Item(2L, "testItem2", "testDescription2", "https://testURL2", 20, 30, System.currentTimeMillis());
    itemRepository.save(item2);
    Item item3 = new Item(3L, "testItem3", "testDescription3", "https://testURL3", 20, 30, System.currentTimeMillis());
    itemRepository.save(item3);
    Item item4 = new Item(4L, "testItem4", "testDescription4", "https://testURL4", 20, 30, System.currentTimeMillis());
    itemRepository.save(item4);

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
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(get("/items/list").header("Authorization", "HW-token " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.items[0]", aMapWithSize(2)))
        .andExpect(jsonPath("$.items[0].name", is("testItem2")))
        .andExpect(jsonPath("$.items[0].photoUrl", is("https://testURL2")))
        .andExpect(jsonPath("$.items[0]", aMapWithSize(2)))
        .andExpect(jsonPath("$.items[1].name", is("testItem3")))
        .andExpect(jsonPath("$.items[1].photoUrl", is("https://testURL3")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUser_whenItemsListWithParameterPage1_statusIsOkAndFirst2SellableItemsWithAdequateDetailsReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    Item item1 = new Item(1L, "testItem1", "testDescription1", "https://testURL1", 20, 30, System.currentTimeMillis());
    itemRepository.save(item1);
    Item item2 = new Item(2L, "testItem2", "testDescription2", "https://testURL2", 20, 30, System.currentTimeMillis());
    itemRepository.save(item2);
    Item item3 = new Item(3L, "testItem3", "testDescription3", "https://testURL3", 20, 30, System.currentTimeMillis());
    itemRepository.save(item3);
    Item item4 = new Item(4L, "testItem4", "testDescription4", "https://testURL4", 20, 30, System.currentTimeMillis());
    itemRepository.save(item4);

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
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(get("/items/list").header("Authorization", "HW-token " + token)
        .param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.items[0]", aMapWithSize(2)))
        .andExpect(jsonPath("$.items[0].name", is("testItem2")))
        .andExpect(jsonPath("$.items[0].photoUrl", is("https://testURL2")))
        .andExpect(jsonPath("$.items[0]", aMapWithSize(2)))
        .andExpect(jsonPath("$.items[1].name", is("testItem3")))
        .andExpect(jsonPath("$.items[1].photoUrl", is("https://testURL3")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUser_whenItemsListWithParameterPage2_statusIsOkAndFirst2SellableItemsWithAdequateDetailsReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

    Item item1 = new Item(1L, "testItem1", "testDescription1", "https://testURL1", 20, 30, System.currentTimeMillis());
    itemRepository.save(item1);
    Item item2 = new Item(2L, "testItem2", "testDescription2", "https://testURL2", 20, 30, System.currentTimeMillis());
    itemRepository.save(item2);
    Item item3 = new Item(3L, "testItem3", "testDescription3", "https://testURL3", 20, 30, System.currentTimeMillis());
    itemRepository.save(item3);
    Item item4 = new Item(4L, "testItem4", "testDescription4", "https://testURL4", 20, 30, System.currentTimeMillis());
    itemRepository.save(item4);

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
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(get("/items/list").header("Authorization", "HW-token " + token)
        .param("page", "2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(1)))
        .andExpect(jsonPath("$.items[0]", aMapWithSize(2)))
        .andExpect(jsonPath("$.items[0].name", is("testItem4")))
        .andExpect(jsonPath("$.items[0].photoUrl", is("https://testURL4")));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUser_whenItemsListWithIncorrectParameter_statusIsBadRequestAndAdequateMessageReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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

    //endregion

    mockMvc.perform(get("/items/list?page=0").header("Authorization", "HW-token " + token))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Page number has to be a positive whole number")));
  }

  //endregion

  //region list specific item

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUserAndASellableItem_whenItemWithExistingItemId_statusIsOkAndAdequateItemDetailsReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(get("/item?id=1").header("Authorization", "HW-token " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", aMapWithSize(6)))
        .andExpect(jsonPath("name", is("testName1")))
        .andExpect(jsonPath("description", is("testDescription1")))
        .andExpect(jsonPath("photoUrl", is("https://testURL1")))
        .andExpect(jsonPath("startingPrice", is(20)))
        .andExpect(jsonPath("purchasePrice", is(30)))
        .andExpect(jsonPath("bids").isArray())
        .andExpect(jsonPath("bids", hasSize(0)));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUserAndANotSellableItem_whenItemWithExistingItemId_statusIsOkAndAdequateItemDetailsReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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
                new CreateItemRequest("testName1", "testDescription1", "https://testURL1", 20F,
                    30F))))
        .andExpect(status().isOk());

    mockMvc.perform(post("/bid").header("Authorization", "HW-token " + token)
        .contentType(contentType)
        .content(
            objectMapper.writeValueAsString(
                new BidRequest(1L, 30))))
        .andExpect(status().isOk());

    //endregion

    mockMvc.perform(get("/item?id=1").header("Authorization", "HW-token " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", aMapWithSize(7)))
        .andExpect(jsonPath("name", is("testName1")))
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

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @Test
  @Transactional
  public void givenAUser_whenItemWithIncorrectItemId_statusIsBadRequestAndAdequateMessageReturned()
      throws Exception {

    //region setup

    User user = new User();
    user.setUsername("testUser1");
    user.setPassword("$2a$10$UMEC8Fal3lDrF4dj9.Rvb.JeyBf2WrYkmOgkqV/Pm4QY6QrTyR2tO");
    user.setAccount(200);
    userRepository.save(user);

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

    //endregion

    mockMvc.perform(get("/item?id=1").header("Authorization", "HW-token " + token))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Item not found")));
  }

  //endregion
}
