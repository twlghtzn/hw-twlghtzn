package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

  private String name;
  private String description;
  private String photoUrl;
  private Float startingPrice;
  private Float purchasePrice;
}
