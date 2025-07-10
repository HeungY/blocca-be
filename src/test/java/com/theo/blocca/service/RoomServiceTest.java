package com.theo.blocca.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.theo.blocca.entity.Room;
import com.theo.blocca.dto.room.CreateRequestDTO;
import com.theo.blocca.repository.RoomRepository;
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
        Room savedRoom = roomRepository.findByCode(code)
                .orElseThrow(()->new IllegalArgumentException("방이 존재하지 않습니다."));

        // then
        assertThat(savedRoom.getFirstPlayer()).isEqualTo("theo");
        assertThat(savedRoom.getCode()).isEqualTo(code);
        assertThat(savedRoom.isActive()).isTrue();
    }
}
