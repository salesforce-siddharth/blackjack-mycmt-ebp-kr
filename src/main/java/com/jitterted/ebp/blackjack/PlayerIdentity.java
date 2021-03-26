package com.jitterted.ebp.blackjack;

public enum PlayerIdentity {
    PLAYER("Player"),
    DEALER("Dealer");
    private String player;
    PlayerIdentity(String playerIdentity){
        this.player = playerIdentity;
    }
    public String toString(){
        return this.player;
    }
    public Boolean isPlayer(){
        return player.equals(PlayerIdentity.PLAYER);
    }
}
