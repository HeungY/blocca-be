package com.theo.quixx.dto.game;

import com.theo.quixx.domain.enums.Action;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameMessage {
    private String code;
    private String id;
    private Action action;
    private Object payload;

    public GameMessage(String code, String id, Action action, Object payload) {
        this.code = code;
        this.id = id;
        this.action = action;
        this.payload = payload;
    }
}