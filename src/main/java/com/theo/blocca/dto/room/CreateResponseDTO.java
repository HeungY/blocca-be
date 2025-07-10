package com.theo.blocca.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateResponseDTO {
    private String code;

    public CreateResponseDTO(String code) {
        this.code = code;
    }
}
