package com.theo.quixx.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public final class CreateRequestDTO {
    private String username;

    public CreateRequestDTO(String username) {
        this.username = username;
    }
}
