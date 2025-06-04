package com.theo.quixx.dto.game.payload;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultPayload implements GamePayload {
    private Map<String, Integer> playerA;
    private Map<String, Integer> playerB;
    private String winner;
}
