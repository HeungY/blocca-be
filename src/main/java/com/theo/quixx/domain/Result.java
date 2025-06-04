package com.theo.quixx.domain;

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

        Map<String, Integer> scoreA = calculateScore(players.get(idA));
        Map<String, Integer> scoreB = calculateScore(players.get(idB));

        int totalA = scoreA.get(idA);
        int totalB = scoreB.get(idB);

        String winner = totalA == totalB ? "DRAW" : (totalA > totalB ? idA : idB);

        return new ResultPayload(scoreA, scoreB, winner);
    }

    private Map<String, Integer> calculateScore(Player player) {
        Map<String, Integer> score = new HashMap<>();

        int redScore = scoreSet[player.getBoard().get(Color.RED).size()];
        int yellowScore = scoreSet[player.getBoard().get(Color.YELLOW).size()];
        int greenScore = scoreSet[player.getBoard().get(Color.GREEN).size()];
        int blueScore = scoreSet[player.getBoard().get(Color.BLUE).size()];

        int failScore = player.getFailCount() * 5;

        int colorScore = redScore + yellowScore + greenScore + blueScore;
        int resultScore = colorScore - failScore;

        score.put(player.getId(), resultScore);
        score.put("RED", redScore);
        score.put("YELLOW", yellowScore);
        score.put("GREEN", greenScore);
        score.put("BLUE", blueScore);
        score.put("FAIL", failScore);

        return score;
    }


}
