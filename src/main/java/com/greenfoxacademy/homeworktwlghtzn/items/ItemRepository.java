package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

  @Query(value = "SELECT item_id AS id, name, description, photo_url AS photoURL, starting_price AS startingPrice, purchase_price AS purchasePrice, is_sellable AS isSellable FROM items i WHERE i.created_at = :createdAt", nativeQuery = true)
  CreateItemResponse findByCreatedAt(Date createdAt);
}
