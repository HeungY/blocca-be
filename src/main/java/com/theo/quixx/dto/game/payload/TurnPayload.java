package com.theo.quixx.dto.game.payload;

import com.theo.quixx.domain.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TurnPayload implements GamePayload{
    private boolean success;
    private String phase;
    private Color color;
    private int number;
    private String turnPlayer;
}
