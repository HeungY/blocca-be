package com.theo.quixx.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
public class Turn {
    private final String playerA;
    private final String playerB;
    private String currentPlayer;

    @Builder
    public Turn(String playerA, String playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.currentPlayer = playerA;
    }

    public void changeTurn() {
        if (currentPlayer.equals(playerA)) {
            currentPlayer = playerB;
            return;
        }
        currentPlayer = playerA;
    }

    public String getOpponent() {
        return currentPlayer.equals(playerA) ? playerB : playerA;
    }

}
