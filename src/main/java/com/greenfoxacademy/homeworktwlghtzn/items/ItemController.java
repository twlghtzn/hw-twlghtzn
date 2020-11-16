package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import com.greenfoxacademy.homeworktwlghtzn.items.dtos.ItemDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/items/list")
  public ResponseEntity<List<ItemDTO>> listItems(
      @RequestParam(name = "page", required = false) Integer page) {
    int pageToLoad;
    if (page == null) {
      pageToLoad = 1;
    } else {
      pageToLoad = page;
    }
    return ResponseEntity.status(HttpStatus.OK).body(itemService.listSellableItems(pageToLoad));
  }

  @GetMapping("/item")
  public ResponseEntity<Item> showSpecificItem(@RequestParam(name = "id") Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(itemService.getSpecificItem(id));
  }
}
