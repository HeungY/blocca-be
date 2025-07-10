package com.theo.blocca.dto.game.payload;

import com.theo.blocca.domain.enums.Color;
import java.util.Set;
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
    private Set<Color> lockedColors;
    private boolean waitOpponentPick;
}
