package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.homeworktwlghtzn.items.Item;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bids")
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private long bidId;
  @ManyToOne
  private User user;
  private int sum;
  @Column(name = "bid_created_at")
  @JsonIgnore
  private long createdAt;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  private Item item;

  public Bid() {
    createdAt = System.currentTimeMillis();
  }

  public Bid(User user, int sum, Item item, long createdAt) {
    this.user = user;
    this.sum = sum;
    createdAt = System.currentTimeMillis();
    this.item = item;
    this.createdAt = createdAt;
  }

  @JsonGetter("username")
  public String getUser() {
    return user.getUsername();
  }
}
