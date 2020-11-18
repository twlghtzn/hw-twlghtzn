package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.greenfoxacademy.homeworktwlghtzn.bids.dtos.BidRequest;
import com.greenfoxacademy.homeworktwlghtzn.items.Item;
import com.greenfoxacademy.homeworktwlghtzn.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
                                       Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return ResponseEntity.status(HttpStatus.OK)
        .body(bidService.placeBid(bidRequest, userPrincipal.getId()));
  }
}
