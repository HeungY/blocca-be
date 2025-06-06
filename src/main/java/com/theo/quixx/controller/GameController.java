package com.theo.quixx.controller;

import com.theo.quixx.dto.game.GameMessage;
import com.theo.quixx.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @MessageMapping("/game")
    public void handleGame(GameMessage gameMessage) {
        gameService.handleGameMessage(gameMessage);
    }
}
