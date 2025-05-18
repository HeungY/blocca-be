package com.theo.quixx.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.theo.quixx.domain.Room;
import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void 정상적인_방_생성_테스트() {
        //given
        CreateRequestDTO requestDTO = new CreateRequestDTO("theo");

        // when
        String code = roomService.createRoom(requestDTO).getCode();
        Room savedRoom = roomRepository.findByCode(code);

        // then
        assertThat(savedRoom.getFirstPlayer()).isEqualTo("theo");
        assertThat(savedRoom.getCode()).isEqualTo(code);
        assertThat(savedRoom.isActive()).isTrue();
    }
}
