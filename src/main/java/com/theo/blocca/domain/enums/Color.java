package com.theo.blocca.domain.enums;

import lombok.Getter;

public enum Color {
    RED(true, 12),
    YELLOW(true, 12),
    GREEN(false, 2),
    BLUE(false, 2);

    private final boolean isAscending;
    @Getter
    private final int finalNumber;

    Color(boolean isAscending, int finalNumber) {
        this.isAscending = isAscending;
        this.finalNumber = finalNumber;
    }

    public boolean isAscending() {
        return isAscending;
    }

}