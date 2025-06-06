package com.theo.quixx.dto.game.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DicePayload implements GamePayload {
    private int white1;
    private int white2;
    private int red;
    private int yellow;
    private int green;
    private int blue;
}
