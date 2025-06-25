package com.theo.quixx.dto.game.payload;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultPayload implements GamePayload {
    private Map<String, String> playerA;
    private Map<String, String> playerB;
    private String winner;
}
