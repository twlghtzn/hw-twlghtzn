package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.items.dtos.CreateItemResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

  @Query(value =
      "SELECT id, name, description, photo_url AS photoUrl, starting_price AS startingPrice, " +
          "purchase_price AS purchasePrice, is_sellable AS isSellable FROM items i WHERE i.item_created_at = :createdAt", nativeQuery = true)
  CreateItemResponse findByCreatedAt(long createdAt);

  @Query(value = "SELECT * FROM items i WHERE i.is_sellable = 1 LIMIT :itemCount OFFSET :pageStart", nativeQuery = true)
  List<Item> findAllSellableItems(int pageStart, int itemCount);

  @Query(value = "select * from items i left join bids b on i.id = b.item_id WHERE id = :id", nativeQuery = true)
  Optional<Item> findItemById(long id);
}
