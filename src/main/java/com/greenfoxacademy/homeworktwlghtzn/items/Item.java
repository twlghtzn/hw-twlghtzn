package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.bids.Bid;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Entity
@Validated
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long itemId;
  @Column(length = 100)
  private String name;
  @Column(length = 100)
  private String description;
  @Column(length = 100, name = "photo_url")
  @URL
  private String photoURL;
  private int startingPrice;
  private int purchasePrice;
  @OneToMany
  private List<Bid> bids;
  private boolean isSellable;

  public Item() {
    isSellable = true;
  }

  public Item(String name, String description,
              @URL String photoURL, int startingPrice, int purchasePrice) {
    this.name = name;
    this.description = description;
    this.photoURL = photoURL;
    this.startingPrice = startingPrice;
    this.purchasePrice = purchasePrice;
    isSellable = true;
  }
}
