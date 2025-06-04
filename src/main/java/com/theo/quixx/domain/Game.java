package com.theo.quixx.domain;

import com.theo.quixx.dto.game.payload.DicePayload;
import com.theo.quixx.dto.game.payload.ResultPayload;
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
                dice.getBlue(),
                dice.getGreen());
    }

    /**
     * 모든 플레이어가 흰색 주사위 합으로 선택
     **/
    public MarkResult markWhite(String playerId, Color color, int number) {
        if (!currentPhase.equals(Phase.WHITE_PICK_PHASE)) {
            return MarkResult.FAILURE;
        }
        if (isWhiteSelected.contains(playerId)) {
            return MarkResult.FAILURE;
        }

        Player player = players.get(playerId);

        if (number == 0) {
            if (turn.getCurrentPlayer().equals(playerId)) {
                isTurnPlayerSkipWhite = true;
            }
            isWhiteSelected.add(playerId);

            if (isWhiteSelected.size() == 2) {
                currentPhase = Phase.COLOR_PICK_PHASE;
            }
            return MarkResult.SUCCESS;
        }



        if (player.canMark(color, number)) {
            player.mark(color, number);

            isWhiteSelected.add(playerId);

            if (isWhiteSelected.size() == 2) {
                currentPhase = Phase.COLOR_PICK_PHASE;
                players.values().forEach(Player::lockColor);
                if (isGameOver()) {
                    currentPhase = Phase.END_GAME_PHASE;
                    return MarkResult.END;
                }
            }

            return MarkResult.SUCCESS;
        }
        return MarkResult.FAILURE;
    }

    /**
     * 현재 플레이어만 색상 선택 가능
     **/
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
            return MarkResult.SUCCESS;
        }

        if (player.canMark(color, number)) {
            player.mark(color, number);
            isColorSelected = true;
            if (isGameOver()) {
                currentPhase = Phase.END_GAME_PHASE;
                return MarkResult.END;
            }
            player.lockColor();
            endTurn();

            return MarkResult.SUCCESS;
        }
        return MarkResult.FAILURE;
    }

    /**
     * 턴 종료
     **/
    public void endTurn() {
        turn.changeTurn();
        isDiceRolled = false;
        isColorSelected = false;
        isTurnPlayerSkipWhite = false;
        isWhiteSelected.clear();
        currentPhase = Phase.DICE_ROLL_PHASE;
    }

    public void restartGame(String playerId) {
        if(!currentPhase.equals(Phase.END_GAME_PHASE)) return;

        restartAgree.add(playerId);

        if(restartAgree.size() == 2) {
            isDiceRolled = false;
            isColorSelected = false;
            isTurnPlayerSkipWhite = false;
            isWhiteSelected.clear();
            restartAgree.clear();
            currentPhase = Phase.DICE_ROLL_PHASE;
        }

    }

    public ResultPayload getResult(){
        Result result = new Result(players);
        return result.getResult();
    }

    public String currentTurnPlayer(){
        return turn.getCurrentPlayer();
    }


    /**
     * 게임 종료 조건: 잠금 2개 또는 실패 4회
     **/
    public boolean isGameOver() {
        return getLockedColorCount() >= 2 || isAnyPlayerFailed();
    }


    /**
     * 잠긴 색상 개수 반환
     **/

    public long getLockedColorCount() {
        return players.values().stream()
                .flatMap(p -> p.getLockedColors().stream())
                .distinct()
                .count();
    }

    /**
     * 플레이어 중 하나라도 4회 실패했는지 여부
     **/
    public boolean isAnyPlayerFailed() {
        return players.values().stream().anyMatch(p -> p.getFailCount() >= 4);
    }

}