package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.RequestIncorrectException;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public CreateItemResponse createNewItem(CreateItemRequest createItemRequest) {
    Map<String, Boolean> fieldCheck = checkRequestFields(createItemRequest);
    if (fieldCheck.containsValue(false)) {
      String message = composeMessageForMissingFields(fieldCheck);
      throw new RequestIncorrectException(message);
    } else {
      String name = createItemRequest.getName();
      String description = createItemRequest.getDescription();
      String photoUrl = createItemRequest.getPhotoUrl();
      int startingPrice = createItemRequest.getStartingPrice();
      int purchasingPrice = createItemRequest.getPurchasePrice();
      if (startingPrice <= 0) {
        throw new RequestIncorrectException("Starting price too low");
      }
      if (purchasingPrice < startingPrice) {
        throw new RequestIncorrectException("Purchase price is lower than starting price");
      }
      Date createdAt = new Date();
      itemRepository
          .save(new Item(name, description, photoUrl, startingPrice, purchasingPrice, createdAt));
      return itemRepository.findByCreatedAt(createdAt);
    }
  }

  public Map<String, Boolean> checkRequestFields(CreateItemRequest createItemRequest) {
    Map<String, Boolean> fieldCheck = new HashMap<>();
    fieldCheck.put("hasName", true);
    fieldCheck.put("hasDescription", true);
    fieldCheck.put("hasPhotoUrl", true);
    fieldCheck.put("hasStartingPrice", true);
    fieldCheck.put("hasPurchasePrice", true);
    if (createItemRequest.getName() == null || createItemRequest.getName().isEmpty()) {
      fieldCheck.replace("hasName", false);
    }
    if (createItemRequest.getDescription() == null ||
        createItemRequest.getDescription().isEmpty()) {
      fieldCheck.replace("hasDescription", false);
    }
    if (createItemRequest.getPhotoUrl() == null || createItemRequest.getPhotoUrl().isEmpty()) {
      fieldCheck.replace("hasPhotoUrl", false);
    }
    if (createItemRequest.getStartingPrice() == null) {
      fieldCheck.replace("hasStartingPrice", false);
    }
    if (createItemRequest.getPurchasePrice() == null) {
      fieldCheck.replace("hasPurchasePrice", false);
    }
    return fieldCheck;
  }

  public String composeMessageForMissingFields(Map<String, Boolean> fieldCheck) {
    StringBuilder message = new StringBuilder();
    message.append("Missing parameter(s): ");
    if (!fieldCheck.get("hasName")) {
      message.append("name, ");
    }
    if (!fieldCheck.get("hasDescription")) {
      message.append("description, ");
    }
    if (!fieldCheck.get("hasPhotoUrl")) {
      message.append("photo URL, ");
    }
    if (!fieldCheck.get("hasStartingPrice")) {
      message.append("starting price, ");
    }
    if (!fieldCheck.get("hasPurchasePrice")) {
      message.append("purchase price, ");
    }
    message.delete(message.length() - 2, message.length());
    return message.toString();
  }
}
