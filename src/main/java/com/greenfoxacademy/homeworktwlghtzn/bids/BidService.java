package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.greenfoxacademy.homeworktwlghtzn.bids.dtos.BidRequest;
import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.RequestIncorrectException;
import com.greenfoxacademy.homeworktwlghtzn.items.Item;
import com.greenfoxacademy.homeworktwlghtzn.items.ItemService;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import com.greenfoxacademy.homeworktwlghtzn.users.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

  private final ItemService itemService;
  private final BidRepository bidRepository;
  private final UserService userService;

  @Autowired
  public BidService(ItemService itemService,
                    BidRepository bidRepository, UserService userService) {
    this.itemService = itemService;
    this.bidRepository = bidRepository;
    this.userService = userService;
  }

  public Item placeBid(BidRequest bidRequest, String username) {
    Item item = itemService.getSpecificItem(bidRequest.getItemId());
    User user = userService.getUserByUsername(username);
    if (!item.getIsSellable()) {
      throw new RequestIncorrectException("Item can't be bought");
    } else if (bidRequest.getBid() > user.getAccount()) {
      throw new RequestIncorrectException("You don't have enough money to place the bid");
    } else if (bidRequest.getBid() < item.getStartingPrice()) {
      throw new RequestIncorrectException("Bid is too low");
    } else if (item.getBids().size() > 0 &&
        bidRequest.getBid() <= item.getBids().get(item.getBids().size() - 1).getSum()) {
      throw new RequestIncorrectException("Bid is too low");
    } else {
      long createdAt = System.currentTimeMillis();
      bidRepository.save(new Bid(user, bidRequest.getBid(), item, createdAt));
      List<Bid> bids = item.getBids();
      bids.add(bidRepository.findBidByCreatedAt(createdAt));
      if (bidRequest.getBid() >= item.getPurchasePrice()) {
        item.setBuyer(user.getUsername());
        item.setIsSellable(false);
        itemService.updateItem(item);
        user.setAccount(user.getAccount() - bidRequest.getBid());
        userService.updateUser(user);
      }
      return itemService.getSpecificItem(bidRequest.getItemId());
    }
  }
}
