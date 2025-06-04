package com.theo.quixx.dto.game.payload;

import com.theo.quixx.domain.Color;

public class TurnPayload extends MarkPayload{
    private String turnPlayer;

    public TurnPayload(boolean success, String phase, Color color, int number, String turnPlayer) {
        super(success, phase, color, number);
        this.turnPlayer = turnPlayer;
    }
}
