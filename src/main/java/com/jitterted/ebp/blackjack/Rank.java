package com.jitterted.ebp.blackjack;

public class Rank {
  private final String rank;

  public Rank(String rank) {
    this.rank = rank;
  }

  public static Rank of(String rank) {
    return new Rank(rank);
  }

  public int rankValue() {
    if (isRankJQK()) {
      return 10;
    } else if (isAce()) {
      return 1;
    } else {
      return Integer.parseInt(rank);
    }
  }

  private boolean isAce() {
    return rank.equals("A");
  }

  private boolean isRankJQK() {
    return "JQK".contains(rank);
  }

  public String display() {
    return rank;
  }
}
