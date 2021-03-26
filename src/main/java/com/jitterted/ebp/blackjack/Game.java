package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

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

  private static Player player;
  private static Player dealer;

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

  private static void playGame() {
    Game game = new Game();

    String input;
    do {
      game.initialDeal();
      game.play();
      input = player.continueGameInput();
    } while (input.equalsIgnoreCase("y"));
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  public Game() {
    deck = new Deck();
    player = new Player(PlayerIdentity.PLAYER);
    dealer = new Player(PlayerIdentity.DEALER);
  }

  public void initialDeal() {

    // deal first round of cards, players first
    player.draw(deck);

    // deal next round of cards
    dealer.draw(deck);
  }

  private boolean playRounds(boolean playerBusted){
    while (!playerBusted) {
      displayGameState();
      String playerChoice = player.inputFromPlayer().toLowerCase();
      if (checkIfStartsWithGivenLetter(playerChoice, "s")) {
        break;
      }
      if (checkIfStartsWithGivenLetter(playerChoice, "h")) {
        player.draw(deck);
        playerBusted = player.isBusted();
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
    while (dealer.shouldHit()) {
      dealer.draw(deck);
    }
  }

  private void handleGameOutcome() {
    if (player.isBusted()) {
      System.out.println(outcomeMessage.PLAYER_BUST);
    } else if (dealer.isBusted()) {
      System.out.println(outcomeMessage.DEALER_BUST);
    } else if (player.beats(dealer)) {
      System.out.println(outcomeMessage.PLAYER_WINS);
    } else if (player.pushesWith(dealer)) {
      System.out.println(outcomeMessage.PUSH);
    } else {
      System.out.println(outcomeMessage.PLAYER_LOST);
    }
  }


  private void displayGameState() {
    clearScreen();
    dealer.displayHand(false);
    player.displayHand(false);
  }

  private void displayFinalGameState() {
    clearScreen();
    dealer.displayHand(true);
    player.displayHand(true);
  }


  private void clearScreen() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
  }
  // second card is the hole card, which is hidden


  public void playerDeposits(int amount) {
    player.playerDeposits(amount);
  }

  public void playerBets(int betAmount) {
    player.playerBets(betAmount);
  }

  public int playerBalance() {
    return player.playerBalance();
  }

  public void playerWins() {
    player.playerWins();
  }

  public void playerLoses() {
    player.playerLoses();
  }

  public void playerTies() {
    player.playerTies();
  }
}
