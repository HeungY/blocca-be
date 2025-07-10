package com.theo.blocca.controller;

import com.theo.blocca.dto.game.GameMessage;
import com.theo.blocca.service.GameService;
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
