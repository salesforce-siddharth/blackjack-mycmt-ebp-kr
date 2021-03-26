package com.jitterted.ebp.blackjack;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Player {


    private int balance = 0;
    private int bet = 0;
    private int totalBet = 0;
    private Hand hand = new Hand();
    PlayerIdentity playerIdentity;

    public Player(PlayerIdentity playerIdentity){
        this.playerIdentity = playerIdentity;
    }

    public static String continueGameInput(){
        System.out.println("Play again? (y/n):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public void draw(Deck deck) {
        this.hand.add(deck.draw());
    }

    public String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public boolean isBusted(){
        return hand.isBusted();
    }

    public boolean shouldHit(){
        return hand.shouldHit();
    }

    public boolean beats(Player player){
        return this.hand.beats(player.hand);
    }

    public boolean pushesWith(Player player){
        return this.hand.pushesWith(player.hand);
    }

    public void displayHand(boolean isFinal){
        if (this.playerIdentity.isPlayer()){
            displayHandPlayer();
        }
        else{
            displayHandDealer(isFinal);
        }
    }

    private void displayHandPlayer(){
        System.out.println();
        System.out.println("Player has: ");
        hand.displayHand();
        System.out.println(" (" + hand.value() + ")");
    }

    private void displayHandDealer(boolean isFinal){
        if (isFinal){
            displayFinalHandDealer();
        }
        else{
            displayNotFinalHandDealer();
        }
    }

    private void displayFinalHandDealer(){
        System.out.println("Dealer has: ");
        hand.displayHand();
        System.out.println(" (" + hand.value() + ")");
    }

    private void displayNotFinalHandDealer(){
        displayDealerUpCard();
        displayDealerHoleCard();
    }

    private void displayDealerUpCard() {
        System.out.println("Dealer has: ");
        System.out.println(hand.displayFirstCard()); // first card is Face Up
    }

    private void displayDealerHoleCard() {
        System.out.print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

    public void playerDeposits(int amount) {
        this.balance += amount;
    }

    public void playerBets(int betAmount) {
        this.bet = betAmount;
        this.balance -= betAmount;
    }

    public int playerBalance() {
        return this.balance;
    }

    public void playerWins() {
        this.balance += this.bet * 2;
    }

    public void playerLoses() {
        this.balance += this.bet * 0;
    }

    public void playerTies() {
        this.balance += this.bet * 1;
    }

    public int totalBetAmount(){
        return totalBet;
    }
}
