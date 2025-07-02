package com.theo.quixx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theo.quixx.domain.Game;
import com.theo.quixx.domain.enums.Action;
import com.theo.quixx.domain.enums.Color;
import com.theo.quixx.domain.enums.MarkResult;
import com.theo.quixx.dto.game.GameMessage;
import com.theo.quixx.dto.game.ResponseMessage;
import com.theo.quixx.dto.game.payload.MarkPayload;
import com.theo.quixx.dto.game.payload.TurnPayload;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, Game> gameMap = new ConcurrentHashMap<>();

    public void createGame(String roomCode, String playerA, String playerB) {
        gameMap.put(roomCode, new Game(roomCode, playerA, playerB));
    }

    public void handleGameMessage(GameMessage message) {
        String code = message.getCode();
        String playerId = message.getId();
        Action action = message.getAction();

        Game game = gameMap.get(code);
        if (game == null) {
            throw new IllegalArgumentException("해당 코드로 개설된 방이 없습니다.");
        }

        switch (action) {
            case ROLL_DICE -> {
                boolean rolled = game.rollDice(playerId);
                if (rolled) {
                    messagingTemplate.convertAndSend("/topic/room/" + code,
                            ResponseMessage.builder()
                                    .code(code)
                                    .id("SYSTEM")
                                    .action(Action.ROLL_DICE)
                                    .payload(game.diceNumbers())
                                    .build());
                }
            }

            case MARK_WHITE -> {
                ObjectMapper objectMapper = new ObjectMapper();
                MarkPayload payloadMap = objectMapper.convertValue(message.getPayload(), MarkPayload.class);
                Color color = payloadMap.getColor();
                int number = payloadMap.getNumber();

                MarkResult result = game.markWhite(playerId, color, number);

                messagingTemplate.convertAndSend("/topic/room/" + code,
                        ResponseMessage.builder()
                                .code(code)
                                .id(playerId)
                                .action(Action.MARK_WHITE)
                                .payload(new MarkPayload(
                                        result != MarkResult.FAILURE,
                                        game.getCurrentPhase().name(),
                                        color,
                                        number,
                                        game.getFailCount(playerId),
                                        game.getLockedColors(),
                                        game.isWaitOpponentWhitePick()
                                ))
                                .build());

                if (result == MarkResult.END) {
                    messagingTemplate.convertAndSend("/topic/room/" + code,
                            ResponseMessage.builder()
                                    .code(code)
                                    .id("SYSTEM")
                                    .action(Action.GAME_END)
                                    .payload(game.getResult())
                                    .build());
                }
            }

            case MARK_COLOR -> {
                ObjectMapper objectMapper = new ObjectMapper();
                MarkPayload payloadMap = objectMapper.convertValue(message.getPayload(), MarkPayload.class);
                Color color = payloadMap.getColor();
                int number = payloadMap.getNumber();

                MarkResult result = game.markColor(playerId, color, number);

                messagingTemplate.convertAndSend("/topic/room/" + code,
                        ResponseMessage.builder()
                                .code(code)
                                .id(playerId)
                                .action(Action.MARK_COLOR)
                                .payload(new MarkPayload(
                                        result != MarkResult.FAILURE,
                                        game.getCurrentPhase().name(),
                                        color,
                                        number,
                                        game.getFailCount(playerId),
                                        game.getLockedColors(),
                                        game.isWaitOpponentWhitePick()
                                ))
                                .build());

                if (result == MarkResult.END) {
                    messagingTemplate.convertAndSend("/topic/room/" + code,
                            ResponseMessage.builder()
                                    .code(code)
                                    .id("SYSTEM")
                                    .action(Action.GAME_END)
                                    .payload(game.getResult())
                                    .build());
                } else if (result == MarkResult.SUCCESS) {
                    messagingTemplate.convertAndSend("/topic/room/" + code,
                            ResponseMessage.builder()
                                    .code(code)
                                    .id("SYSTEM")
                                    .action(Action.TURN_CHANGE)
                                    .payload(new TurnPayload(true,
                                            game.getCurrentPhase().name(),
                                            color,
                                            number,
                                            game.currentTurnPlayer()))
                                    .build());
                }
            }
            case GAME_RESTART -> {
                game.restartGame(playerId);
            }
        }
    }
}