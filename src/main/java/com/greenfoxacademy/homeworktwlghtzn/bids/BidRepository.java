package com.greenfoxacademy.homeworktwlghtzn.bids;

import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {

  Bid findBidByCreatedAt(long createdAt);
}
