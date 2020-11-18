package com.greenfoxacademy.homeworktwlghtzn.items.dtos;

public interface CreateItemResponse {

  long getId();

  String getName();

  String getDescription();

  String getPhotoUrl();

  Integer getStartingPrice();

  Integer getPurchasePrice();
}
