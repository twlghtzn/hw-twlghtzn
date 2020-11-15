package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping("/items/create")
  public ResponseEntity<CreateItemResponse> createItem(
      @RequestBody CreateItemRequest createItemRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(itemService.createNewItem(createItemRequest));
  }
}
