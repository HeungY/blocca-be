package com.theo.blocca.domain;

import com.theo.blocca.domain.enums.Color;
import com.theo.blocca.domain.enums.MarkResult;
import com.theo.blocca.domain.enums.Phase;
import com.theo.blocca.dto.game.payload.DicePayload;
import com.theo.blocca.dto.game.payload.ResultPayload;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class Game {

    private final String roomCode;
    private final Dice dice = new Dice();
    private final Turn turn;
    private final Map<String, Player> players = new HashMap<>();

    // 상태관리
    private boolean isDiceRolled = false;
    private boolean isColorSelected = false;
    private boolean isTurnPlayerSkipWhite = false;
    private final Set<String> isWhiteSelected = new HashSet<>();
    private Phase currentPhase = Phase.DICE_ROLL_PHASE;
    private final Set<String> restartAgree = new HashSet<>();
    private Set<Color> lockedColors = new HashSet<>();
    private boolean waitOpponentWhitePick = false;


    public Game(String roomCode, String playerA, String playerB) {
        this.roomCode = roomCode;
        this.turn = Turn.builder()
                .playerA(playerA)
                .playerB(playerB)
                .build();

        players.put(playerA, new Player(playerA));
        players.put(playerB, new Player(playerB));
    }

    /**
     * 주사위 굴림
     **/
    public boolean rollDice(String player) {
        if (!currentPhase.equals(Phase.DICE_ROLL_PHASE)) {
            return false;
        }
        if (!turn.getCurrentPlayer().equals(player)) {
            return false;
        }
        if (isDiceRolled) {
            return false;
        }

        dice.roll();

        isDiceRolled = true;
        currentPhase = Phase.WHITE_PICK_PHASE;
        return true;    // 여기서 true 반환 말고 dice 숫자 반환하자
    }

    public DicePayload diceNumbers() {

        return new DicePayload(
                dice.getWhite1(),
                dice.getWhite2(),
                dice.getRed(),
                dice.getYellow(),
                dice.getGreen(),
                dice.getBlue());
    }

    public MarkResult markWhite(String playerId, Color color, int number) {
        if (!currentPhase.equals(Phase.WHITE_PICK_PHASE)) {
            return MarkResult.FAILURE;
        }
        if (isWhiteSelected.contains(playerId)) {
            return MarkResult.FAILURE;
        }

        if (number == 0) {
            if (turn.getCurrentPlayer().equals(playerId)) {
                isTurnPlayerSkipWhite = true;
            }
        } else {
            if (number != (dice.getWhite1() + dice.getWhite2())) {
                return MarkResult.FAILURE;
            }
            Player player = players.get(playerId);
            if (!player.canMark(color, number, lockedColors)) {
                return MarkResult.FAILURE;
            }
            player.mark(color, number);
        }

        isWhiteSelected.add(playerId);

        if (isWhiteSelected.size() == 2) {
            currentPhase = Phase.COLOR_PICK_PHASE;

            for (Player p : players.values()) {
                for (Color c : Color.values()) {
                    if (p.getBoard().get(c).contains(c.getFinalNumber())) {
                        lockedColors.add(c);
                    }
                }
            }
            waitOpponentWhitePick = false;

            if (isGameOver()) {
                currentPhase = Phase.END_GAME_PHASE;
                return MarkResult.END;
            }
        }

        if (isWhiteSelected.size() == 1) {
            waitOpponentWhitePick = true;
        }

        return MarkResult.SUCCESS;
    }

    public MarkResult markColor(String playerId, Color color, int number) {
        if (!currentPhase.equals(Phase.COLOR_PICK_PHASE)) {
            return MarkResult.FAILURE;
        }
        if (!turn.getCurrentPlayer().equals(playerId)) {
            return MarkResult.FAILURE;
        }
        if (isColorSelected) {
            return MarkResult.FAILURE;
        }

        Player player = players.get(playerId);

        if (number == 0) {
            if (isTurnPlayerSkipWhite) {
                player.fail();
            }
            isColorSelected = true;
            if (isGameOver()) {
                return MarkResult.END;
            }
            endTurn();
            return MarkResult.SUCCESS;
        }

        switch (color) {
            case RED:
                if (number != (dice.getWhite1() + dice.getRed()) && number != dice.getWhite2() + dice.getRed()) {
                    return MarkResult.FAILURE;
                }
                break;
            case YELLOW:
                if (number != (dice.getWhite1() + dice.getYellow()) && number != dice.getWhite2() + dice.getYellow()) {
                    return MarkResult.FAILURE;
                }
                break;
            case GREEN:
                if (number != (dice.getWhite1() + dice.getGreen()) && number != dice.getWhite2() + dice.getGreen()) {
                    return MarkResult.FAILURE;
                }
                break;
            case BLUE:
                if (number != (dice.getWhite1() + dice.getBlue()) && number != dice.getWhite2() + dice.getBlue()) {
                    return MarkResult.FAILURE;
                }
                break;

        }

        if (player.canMark(color, number, lockedColors)) {
            player.mark(color, number);
            isColorSelected = true;
            if (isGameOver()) {
                currentPhase = Phase.END_GAME_PHASE;
                return MarkResult.END;
            }
            if (number == color.getFinalNumber()) {
                lockedColors.add(color);
            }

            endTurn();

            return MarkResult.SUCCESS;
        }
        return MarkResult.FAILURE;
    }

    public void endTurn() {
        turn.changeTurn();
        isDiceRolled = false;
        isColorSelected = false;
        isTurnPlayerSkipWhite = false;
        isWhiteSelected.clear();
        currentPhase = Phase.DICE_ROLL_PHASE;
    }

    public void restartGame(String playerId) {
        if (!currentPhase.equals(Phase.END_GAME_PHASE)) {
            return;
        }

        restartAgree.add(playerId);

        if (restartAgree.size() == 2) {
            isDiceRolled = false;
            isColorSelected = false;
            isTurnPlayerSkipWhite = false;
            isWhiteSelected.clear();
            restartAgree.clear();
            currentPhase = Phase.DICE_ROLL_PHASE;
            lockedColors.clear();
        }

    }

    public ResultPayload getResult() {
        Result result = new Result(players);
        return result.getResult();
    }

    public String currentTurnPlayer() {
        return turn.getCurrentPlayer();
    }

    public int getFailCount(String playerId) {
        return players.get(playerId).getFailCount();
    }


    public boolean isGameOver() {
        return lockedColors.size() >= 2 || isAnyPlayerFailed();
    }


    public boolean isAnyPlayerFailed() {
        return players.values().stream().anyMatch(p -> p.getFailCount() >= 4);
    }

}