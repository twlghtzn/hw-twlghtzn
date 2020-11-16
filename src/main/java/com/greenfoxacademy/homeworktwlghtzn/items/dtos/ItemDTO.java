package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {

  private String name;
  private String photoURL;
  @JsonInclude(JsonInclude.Include. NON_NULL)
  private BidDTO lastBid;
}
