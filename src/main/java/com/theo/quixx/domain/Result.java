package com.theo.quixx.domain;

import com.theo.quixx.domain.enums.Color;
import com.theo.quixx.dto.game.payload.ResultPayload;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
    private Map<String, Player> players;
    private final int[] scoreSet = {0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78};


    public Result(Map<String, Player> players) {
        this.players = players;
    }

    public ResultPayload getResult() {
        List<String> ids = new ArrayList<>(players.keySet());

        String idA = ids.get(0);
        String idB = ids.get(1);

        Map<String, String> scoreA = calculateScore(players.get(idA));
        Map<String, String> scoreB = calculateScore(players.get(idB));

        int totalA = Integer.parseInt(scoreA.get("totalScore"));
        int totalB = Integer.parseInt(scoreB.get("totalScore"));

        String winner = totalA == totalB ? "DRAW" : (totalA > totalB ? idA : idB);

        return new ResultPayload(scoreA, scoreB, winner);
    }

    private Map<String, String> calculateScore(Player player) {
        Map<String, String> score = new HashMap<>();
        int isRedLock = 0;
        int isYellowLock = 0;
        int isGreenLock = 0;
        int isBlueLock = 0;

        if (player.getBoard().get(Color.RED).contains(Color.RED.getFinalNumber())) {
            isRedLock++;
        }
        if (player.getBoard().get(Color.YELLOW).contains(Color.YELLOW.getFinalNumber())) {
            isYellowLock++;
        }
        if (player.getBoard().get(Color.GREEN).contains(Color.GREEN.getFinalNumber())) {
            isGreenLock++;
        }
        if (player.getBoard().get(Color.BLUE).contains(Color.BLUE.getFinalNumber())) {
            isBlueLock++;
        }

        int redScore = scoreSet[player.getBoard().get(Color.RED).size() + isRedLock];
        int yellowScore = scoreSet[player.getBoard().get(Color.YELLOW).size() + isYellowLock];
        int greenScore = scoreSet[player.getBoard().get(Color.GREEN).size() + isGreenLock];
        int blueScore = scoreSet[player.getBoard().get(Color.BLUE).size() + isBlueLock];

        int failScore = player.getFailCount() * 5;

        int colorScore = redScore + yellowScore + greenScore + blueScore;
        int resultScore = colorScore - failScore;

        score.put("userName", player.getId());
        score.put("totalScore", resultScore + "");
        score.put("RED", redScore + "");
        score.put("YELLOW", yellowScore + "");
        score.put("GREEN", greenScore + "");
        score.put("BLUE", blueScore + "");
        score.put("FAIL", failScore + "");

        return score;
    }


}
