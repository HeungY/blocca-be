package com.theo.blocca.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public final class JoinRequestDTO {
    private String username;
    private String code;

    public JoinRequestDTO(String username, String code) {
        this.username = username;
        this.code = code;
    }

}
