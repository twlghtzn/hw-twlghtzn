package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.bids.Bid;
import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.RequestIncorrectException;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.BidDto;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.ItemDto;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.ListItemDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
      int startingPrice;
      int purchasingPrice;
      Float startingPriceL = createItemRequest.getStartingPrice();
      Float purchasingPriceL = createItemRequest.getPurchasePrice();
      if (startingPriceL % 1 != 0 || purchasingPriceL % 1 != 0) {
        throw new RequestIncorrectException(
            "Starting price and purchase price has to be a positive whole number");
      } else {
        startingPrice = startingPriceL.intValue();
        purchasingPrice = purchasingPriceL.intValue();
      }
      if (startingPrice <= 0) {
        throw new RequestIncorrectException("Starting price too low");
      }
      if (purchasingPrice < startingPrice) {
        throw new RequestIncorrectException("Purchase price is lower than starting price");
      }

      long createdAt = System.currentTimeMillis();
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
      message.append("photoUrl, ");
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

  public ListItemDto listSellableItems(int page) {
    ListItemDto listItemDtos = new ListItemDto();
    List<ItemDto> itemDtos = new ArrayList<>();
    if (page <= 0) {
      throw new RequestIncorrectException("Page number has to be a positive whole number");
    } else {
      int pageStart = (page - 1) * 2;
      int itemCount = 2;
      List<Item> items = itemRepository.findAllSellableItems(pageStart, itemCount);
      for (Item item : items) {
        ItemDto itemDTO = new ItemDto();
        BidDto bidDTO = new BidDto();
        itemDTO.setName(item.getName());
        itemDTO.setPhotoUrl(item.getPhotoUrl());
        if (item.getBids().size() != 0) {
          Bid lastBid = item.getBids().get(item.getBids().size() - 1);
          bidDTO.setUsername(lastBid.getUser());
          bidDTO.setSum(lastBid.getSum());
          itemDTO.setLastBid(bidDTO);
        }
        itemDtos.add(itemDTO);
      }
      listItemDtos.setItems(itemDtos);
    }
    return listItemDtos;
  }

  public Item getSpecificItem(Long id) {
    Optional<Item> item = itemRepository.findItemById(id);
    if (item.isPresent()) {
      return item.get();
    } else {
      throw new RequestIncorrectException("Item not found");
    }
  }

  public void updateItem(Item item) {
    itemRepository.save(item);
  }
}
