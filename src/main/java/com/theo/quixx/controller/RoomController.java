package com.theo.quixx.controller;

import com.theo.quixx.dto.game.GameMessage;
import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.dto.room.CreateResponseDTO;
import com.theo.quixx.dto.room.JoinRequestDTO;
import com.theo.quixx.dto.room.JoinResponseDTO;
import com.theo.quixx.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/hc")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok("Server is alive !");
    }

    @PostMapping("/room/create")
    public CreateResponseDTO createRoom(@RequestBody CreateRequestDTO createRequestDTO) {
        return roomService.createRoom(createRequestDTO);
    }

    @PostMapping("/room/join")
    public JoinResponseDTO joinRoom(@RequestBody JoinRequestDTO joinRequestDTO) {
        return roomService.joinRoom(joinRequestDTO);
    }

    @MessageMapping("/init")
    public void init(GameMessage message){
        roomService.initRoom(message.getCode());
    }

}
