package com.theo.quixx.service;

import static com.theo.quixx.util.Constant.GENERATE_CODE_CHARACTERS;
import static com.theo.quixx.util.Constant.GENERATE_CODE_START;
import static com.theo.quixx.util.Constant.MAX_CODE_LENGTH;

import com.theo.quixx.domain.Room;
import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.dto.room.CreateResponseDTO;
import com.theo.quixx.repository.RoomRepository;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SecureRandom random = new SecureRandom();

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public CreateResponseDTO createRoom(CreateRequestDTO createRequestDTO) {    // 방 생성 후 코드 반환
        String roomCode = roomRepository.save(new Room(createRequestDTO.getUsername(), generateRoomCode())).getCode();
        return new CreateResponseDTO(roomCode);
    }

    private String generateRoomCode() {     // 6자리 대문자 + 숫자 난수 생성
        String codeSet = GENERATE_CODE_CHARACTERS;
        StringBuilder randomCode = new StringBuilder(MAX_CODE_LENGTH);
        for (int i = GENERATE_CODE_START; i < MAX_CODE_LENGTH; i++) {
            randomCode.append(codeSet.charAt(random.nextInt(codeSet.length())));
        }
        return randomCode.toString();
    }
}
