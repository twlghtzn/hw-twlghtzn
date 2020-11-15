package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

public interface CreateItemResponse {

  long getId();

  String getName();

  String getDescription();

  String getPhotoURL();

  Integer getStartingPrice();

  Integer getPurchasePrice();

  Boolean getIsSellable();
}
