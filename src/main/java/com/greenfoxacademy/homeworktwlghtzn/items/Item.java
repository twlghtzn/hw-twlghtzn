package com.greenfoxacademy.homeworktwlghtzn.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenfoxacademy.homeworktwlghtzn.bids.Bid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  @JsonIgnore
  @Column(name = "id")
  private long itemId;
  @Column(length = 100)
  private String name;
  @Column(length = 100)
  private String description;
  @Column(length = 100, name = "photo_url")
  @URL
  private String photoURL;
  private Integer startingPrice;
  private Integer purchasePrice;
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Bid> bids;
  @JsonIgnore
  private Boolean isSellable;
  @JsonIgnore
  @Column(name = "item_created_at")
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
