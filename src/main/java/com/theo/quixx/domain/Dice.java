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

    private int callCount = 0;
    private int test = 1;

    int test() {
        if (callCount % 2 == 0) {
            if (test == 6) {
                test = 1;
            } else {
                test++;
            }
        }
        return test;
    }

    private int test2 = 5;

    int test2() {
        if (callCount % 2 == 0) {
            if (test2 == 6) {
                test2 = 1;
            } else {
                test2++;
            }
        }
        return test2;
    }

    public void roll() {
        callCount++;
//        this.white1 = generateRandomNumber();
//        this.white2 = generateRandomNumber();
        this.white1 = 6;
        this.white2 = 6;    // 테스트를 위한 임시 숫자 할당
        this.red = test();
        this.yellow = test2();
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
