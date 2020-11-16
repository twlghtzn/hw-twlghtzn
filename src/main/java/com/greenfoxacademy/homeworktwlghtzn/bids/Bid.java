package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.homeworktwlghtzn.items.Item;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
  @JoinColumn(name = "username", referencedColumnName = "username")
  private User user;
  @Column(name = "gdb_amount")
  private int GBDAmount;
  @Column(name = "bid_created_at")
  @JsonIgnore
  private long createdAt;
  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Item item;

  public Bid() {
    createdAt = System.currentTimeMillis();
  }

  public Bid(User user, int GBDAmount) {
    this.user = user;
    this.GBDAmount = GBDAmount;
    createdAt = System.currentTimeMillis();
  }
}
