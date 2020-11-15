package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateItemRequest {

  private String name;
  private String description;
  private String photoUrl;
  private Integer startingPrice;
  private Integer purchasePrice;
}
