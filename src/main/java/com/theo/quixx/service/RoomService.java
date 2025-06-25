package com.theo.quixx.service;

import static com.theo.quixx.util.Constant.GENERATE_CODE_CHARACTERS;
import static com.theo.quixx.util.Constant.MAX_CODE_LENGTH;

import com.theo.quixx.domain.enums.Action;
import com.theo.quixx.dto.game.ResponseMessage;
import com.theo.quixx.dto.game.payload.StartPayload;
import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.dto.room.CreateResponseDTO;
import com.theo.quixx.dto.room.JoinRequestDTO;
import com.theo.quixx.dto.room.JoinResponseDTO;
import com.theo.quixx.entity.Room;
import com.theo.quixx.repository.RoomRepository;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final SimpMessagingTemplate messagingTemplate;
    private final RoomRepository roomRepository;
    private final SecureRandom random = new SecureRandom();
    private final GameService gameService;


    public CreateResponseDTO createRoom(CreateRequestDTO createRequestDTO) {    // 방 생성 후 코드 반환
        String roomCode = roomRepository.save(new Room(createRequestDTO.getUsername(), generateUniqueRoomCode())).getCode();
        return new CreateResponseDTO(roomCode);
    }

    public JoinResponseDTO joinRoom(JoinRequestDTO joinRequestDTO) {    // 두번째 유저 방 입장. 리팩토링 필요.
        Room room = roomRepository.findByCode(joinRequestDTO.getCode())
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        if (room.hasSecondPlayer()) {
            throw new IllegalArgumentException("room is full");
        }

        room.join(joinRequestDTO.getUsername());
        gameService.createGame(room.getCode(),room.getFirstPlayer(),room.getSecondPlayer());
        roomRepository.save(room);

        return new JoinResponseDTO(room.getCode());
    }

    public void initRoom(String code) {
        Room room = roomRepository.findByCode(code).orElseThrow();

        messagingTemplate.convertAndSend("/topic/room/"+code,
                ResponseMessage.builder()
                        .code(code)
                        .id("SYSTEM")
                        .action(Action.GAME_INIT)
                        .payload(new StartPayload(
                                room.getFirstPlayer(),
                                room.getSecondPlayer()
                        ))
                        .build());
    }


    private String generateUniqueRoomCode() {   // DB에 중복 코드가 존재하는지 확인
        String code;
        do {
            code = generateRoomCode();
        } while (roomRepository.existsByCode(code));
        return code;
    }

    private String generateRoomCode() {     // 6자리 대문자 + 숫자 난수 생성
        String codeSet = GENERATE_CODE_CHARACTERS;
        StringBuilder randomCode = new StringBuilder(MAX_CODE_LENGTH);
        for (int i = 0; i < MAX_CODE_LENGTH; i++) {
            randomCode.append(codeSet.charAt(random.nextInt(codeSet.length())));
        }
        return randomCode.toString();
    }
}
