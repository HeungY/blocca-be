package com.theo.quixx.dto.game.payload;

import com.theo.quixx.domain.enums.Color;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarkPayload implements GamePayload {
    private boolean success;
    private String phase;
    private Color color;
    private int number;
    private int failCount;
    private List<Color> lockedColors;
}
