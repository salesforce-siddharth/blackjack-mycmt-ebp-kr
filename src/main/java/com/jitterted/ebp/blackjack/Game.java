package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private enum outcomeMessage {
    PLAYER_BUST("You Busted, so you lose.  💸"),
    DEALER_BUST("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_WINS("You beat the Dealer! 💵"),
    PUSH("Push: The house wins, you Lose. 💸"),
    PLAYER_LOST("You lost to the Dealer. 💸");

    private String outcome;

    outcomeMessage(String outcome){
      this.outcome = outcome;
    }

    public String toString(){
      return this.outcome;
    }
  }

  private final Deck deck;

  private Hand dealerHand = new Hand();
  private Hand playerHand = new Hand();
  private int playerBalance = 0;
  private int playerBet = 0;

  public static void main(String[] args) {
    displayWelcomeScreen();
    playGame();
    resetScreen();
  }

  private static void displayWelcomeScreen() {
    System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
  }

  private static String continueGameInput(){
    System.out.println("Play again? (y/n):");
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    return input;
  }

  private static void playGame() {
    Game game = new Game();

    String input;
    do {
      game.initialDeal();
      game.play();
      input = continueGameInput();
    } while (input.equalsIgnoreCase("y"));
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealerHand = new Hand();
    playerHand = new Hand();

    // deal first round of cards, players first
    dealHand();

    // deal next round of cards
    dealHand();
  }

  private void dealHand() {
    drawCardIntoPlayerHand();
    drawCardIntoDealerHand();
  }

  private void drawCardIntoDealerHand() {
    dealerHand.add(deck.draw());
  }

  private void drawCardIntoPlayerHand() {
    playerHand.add(deck.draw());
  }

  private boolean playRounds(boolean playerBusted){
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (checkIfStartsWithGivenLetter(playerChoice, "s")) {
        break;
      }
      if (checkIfStartsWithGivenLetter(playerChoice, "h")) {
        drawCardIntoPlayerHand();
        playerBusted = playerHand.isBusted();
      } else {
        System.out.println(displayInstruction());
      }
    }
    return playerBusted;
  }

  private String displayInstruction() {
    return "You need to [H]it or [S]tand";
  }

  private boolean checkIfStartsWithGivenLetter(String playerChoice, String s) {
    return playerChoice.startsWith(s);
  }

  public void play() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = playRounds(false);

    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      dealerPlays();
    }

    displayFinalGameState();

    handleGameOutcome();
  }

  private void dealerPlays() {
    while (dealerHand.shouldHit()) {
      drawCardIntoDealerHand();
    }
  }

  private void handleGameOutcome() {
    if (playerHand.isBusted()) {
      System.out.println(outcomeMessage.PLAYER_BUST);
    } else if (dealerHand.isBusted()) {
      System.out.println(outcomeMessage.DEALER_BUST);
    } else if (playerHand.beats(dealerHand)) {
      System.out.println(outcomeMessage.PLAYER_WINS);
    } else if (playerHand.pushesWith(dealerHand)) {
      System.out.println(outcomeMessage.PUSH);
    } else {
      System.out.println(outcomeMessage.PLAYER_LOST);
    }
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    clearScreen();
    displayDealerHand();
    displayPlayerHand();
  }

  private void displayFinalGameState() {
    clearScreen();
    displayFinalDealerHand();
    displayPlayerHand();
  }

  private void displayDealerHand() {
    displayDealerUpCard();
    displayDealerHoleCard();
  }

  private void displayDealerUpCard() {
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.displayFirstCard()); // first card is Face Up
  }

  private void clearScreen() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
  }
  // second card is the hole card, which is hidden

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

  private void displayFinalDealerHand() {
    System.out.println("Dealer has: ");
    dealerHand.displayHand();
    System.out.println(" (" + dealerHand.value() + ")");
  }

  private void displayPlayerHand() {
    System.out.println();
    System.out.println("Player has: ");
    playerHand.displayHand();
    System.out.println(" (" + playerHand.value() + ")");
  }

  public void playerDeposits(int amount) {
    playerBalance += amount;
  }

  public void playerBets(int betAmount) {
    playerBet = betAmount;
    playerBalance -= betAmount;
  }

  public int playerBalance() {
    return playerBalance;
  }

  public void playerWins() {
    playerBalance += playerBet * 2;
  }

  public void playerLoses() {
    playerBalance += playerBet * 0;
  }

  public void playerTies() {
    playerBalance += playerBet * 1;
  }
}
