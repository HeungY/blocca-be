package com.theo.blocca.dto.game;

import com.theo.blocca.domain.enums.Action;
import com.theo.blocca.dto.game.payload.GamePayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ResponseMessage {
    private String code;
    private String id;
    private Action action;
    private GamePayload payload;

    public ResponseMessage(String code, String id, Action action, GamePayload payload) {
        this.code = code;
        this.id = id;
        this.action = action;
        this.payload = payload;
    }
}