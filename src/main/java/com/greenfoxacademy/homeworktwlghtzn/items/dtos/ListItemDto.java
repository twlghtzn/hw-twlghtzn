package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListItemDto {

  private List<ItemDto> items;
}
