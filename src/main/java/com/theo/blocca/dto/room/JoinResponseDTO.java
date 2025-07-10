package com.theo.blocca.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoinResponseDTO {
    private String code;

    public JoinResponseDTO(String code) {
        this.code = code;
    }
}
