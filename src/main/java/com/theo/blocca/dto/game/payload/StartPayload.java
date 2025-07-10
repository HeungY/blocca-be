package com.theo.blocca.dto.game.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StartPayload implements GamePayload {
    private final String player1;
    private final String player2;
}
