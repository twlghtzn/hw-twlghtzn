package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.greenfoxacademy.homeworktwlghtzn.bids.dtos.BidRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {

  private final BidService bidService;

  @Autowired
  public BidController(BidService bidService) {
    this.bidService = bidService;
  }

  @PostMapping("/bid")
  public ResponseEntity<Item> placeBid(@RequestBody BidRequest bidRequest,
                                       java.security.Principal user) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(bidService.placeBid(bidRequest, user.getName()));
  }
}
