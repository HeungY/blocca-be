package com.theo.quixx.dto.game.payload;

import com.theo.quixx.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkPayload implements GamePayload {
    private boolean success;
    private String phase;
    private Color color;
    private int number;

}
