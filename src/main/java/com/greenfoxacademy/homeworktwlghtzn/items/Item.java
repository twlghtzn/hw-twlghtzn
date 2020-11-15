package com.greenfoxacademy.homeworktwlghtzn.items;

import com.greenfoxacademy.homeworktwlghtzn.bids.Bid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
  @Min(1)
  private Integer startingPrice;
  @Min(1)
  private Integer purchasePrice;
  @OneToMany
  private List<Bid> bids;
  private Boolean isSellable;
  private Date createdAt;

  public Item() {
    isSellable = true;
    bids = new ArrayList<>();
  }

  public Item(String name, String description,
              @URL String photoURL, int startingPrice, int purchasePrice, Date createdAt) {
    this.name = name;
    this.description = description;
    this.photoURL = photoURL;
    this.startingPrice = startingPrice;
    this.purchasePrice = purchasePrice;
    isSellable = true;
    bids = new ArrayList<>();
    this.createdAt = createdAt;
  }
}
