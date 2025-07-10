package com.theo.blocca.domain;

import com.theo.blocca.domain.enums.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class Player {
    private final String id;
    private final Map<Color, List<Integer>> board;
    private int failCount = 0;

    public Player(String id) {
        this.id = id;
        this.board = new EnumMap<>(Color.class);
        for (Color color : Color.values()) {
            board.put(color, new ArrayList<>());
        }
    }

    public boolean canMark(Color color, int number, Set<Color> lockedColors) {
        if (lockedColors.contains(color)) {
            return false;
        }

        if (number == color.getFinalNumber() && !judgeMarkFinalNumber(color, number)) {
            return false;
        }

        List<Integer> line = board.get(color);
        if (line.isEmpty()) {
            return true;
        }

        int last = line.get(line.size() - 1);
        return color.isAscending() ? number > last : number < last;
    }

    private boolean judgeMarkFinalNumber(Color color, int number) {
        if (number != color.getFinalNumber()) {
            return false;
        }

        List<Integer> line = board.get(color);
        if (line.isEmpty()) {
            return false;
        }

        return line.size() >= 5;
    }


    public String mark(Color color, int number) {
        board.get(color).add(number);
        return id;
    }

    public void fail() {
        failCount++;
    }
}
