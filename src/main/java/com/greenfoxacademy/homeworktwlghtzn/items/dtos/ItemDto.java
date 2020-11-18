package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

  private String name;
  private String photoUrl;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private BidDto lastBid;
}
