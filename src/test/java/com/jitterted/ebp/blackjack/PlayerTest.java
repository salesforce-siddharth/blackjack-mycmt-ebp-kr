package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PlayerTest {

    // Player Ties = get Bet back
    // Player BJ = Bet + Bet + 50% Bet

    @Test
    public void playerWith20Bets10WhenWinsBalanceIs30() throws Exception {
        Player player = createPlayerWithBalanceOf(20);
        player.playerBets(10);

        player.playerWins();

        assertThat(player.playerBalance())
                .isEqualTo(30);
    }

    @Test
    public void playerWith80Bets70WhenTiesBalanceIs80() throws Exception {
        Player player = createPlayerWithBalanceOf(80);
        player.playerBets(70);

        player.playerTies();

        assertThat(player.playerBalance())
                .isEqualTo(80);
    }

    @Test
    public void playerWith35Bets30WhenLosesBalanceIs5() throws Exception {
        Player player = createPlayerWithBalanceOf(35);

        player.playerBets(30);
        player.playerLoses();

        assertThat(player.playerBalance())
                .isEqualTo(5);
    }

    @Test
    public void playerWith40Bets15BalanceIs25() throws Exception {
        Player player = createPlayerWithBalanceOf(40);

        player.playerBets(15);

        assertThat(player.playerBalance())
                .isEqualTo(25);
    }

    @Test
    public void playerDeposits18DollarsBalanceIs18Dollars() throws Exception {
        Player player = createPlayerWithBalanceOf(18);

        assertThat(player.playerBalance())
                .isEqualTo(18);
    }

    @Test
    public void playerGoesBustIfKeepsDrawing() throws Exception {
        Player player = createPlayerWithBalanceOf(100);
        Deck deck = new Deck();
        player.draw(deck);
        while(!player.isBusted()) {
            player.draw(deck);
        }
        assertThat(player.isBusted());
    }


    private Player createPlayerWithBalanceOf(int amount) {
        Player player = new Player(PlayerIdentity.PLAYER);
        player.playerDeposits(amount);
        return player;
    }
}