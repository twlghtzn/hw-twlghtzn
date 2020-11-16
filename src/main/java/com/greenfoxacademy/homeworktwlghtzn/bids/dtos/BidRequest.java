package com.greenfoxacademy.homeworktwlghtzn.bids.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BidRequest {

  private Long itemId;
  private Integer bid;
}
