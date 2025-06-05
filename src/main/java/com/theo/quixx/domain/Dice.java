package com.theo.quixx.domain;

import com.theo.quixx.domain.enums.Color;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Dice {
    private int white1;
    private int white2;
    private int red;
    private int yellow;
    private int green;
    private int blue;

    public void roll() {
        this.white1 = generateRandomNumber();
        this.white2 = generateRandomNumber();
        this.red = generateRandomNumber();
        this.yellow = generateRandomNumber();
        this.green = generateRandomNumber();
        this.blue = generateRandomNumber();
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 6) + 1;
    }

    public Map<Color, Set<Integer>> getAvailableColorSums() {   // 쓸까 말까 고민중..
        Map<Color, Set<Integer>> result = new EnumMap<>(Color.class);

        result.put(Color.RED, Set.of(white1 + red, white2 + red));
        result.put(Color.YELLOW, Set.of(white1 + yellow, white2 + yellow));
        result.put(Color.GREEN, Set.of(white1 + green, white2 + green));
        result.put(Color.BLUE, Set.of(white1 + blue, white2 + blue));

        return result;
    }
}
