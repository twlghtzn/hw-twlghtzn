package com.greenfoxacademy.homeworktwlghtzn.bids;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import java.text.SimpleDateFormat;
import java.util.Date;
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
  private long bidId;
  @ManyToOne
  @JoinColumn(name = "user_name", referencedColumnName = "username")
  private User user;
  @Column(name = "gdb_amount")
  private int GBDAmount;
  private Date date;

  public Bid() {
    date = new Date();
  }

  public Bid(User user, int GBDAmount) {
    this.user = user;
    this.GBDAmount = GBDAmount;
    date = new Date();
  }

  @JsonProperty
  public String getDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    return formatter.format(date);
  }
}
