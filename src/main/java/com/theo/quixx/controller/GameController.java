package com.theo.quixx.controller;

import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.dto.room.CreateResponseDTO;
import com.theo.quixx.dto.room.JoinRequestDTO;
import com.theo.quixx.dto.room.JoinResponseDTO;
import com.theo.quixx.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GameController {

    private final RoomService roomService;

    public GameController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/hc")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok("Server is alive !");
    }

    @PostMapping("/room/create")
    public CreateResponseDTO createRoom(CreateRequestDTO createRequestDTO) {
        return roomService.createRoom(createRequestDTO);
    }

    @PostMapping("/room/join")
    public JoinResponseDTO joinRoom(JoinRequestDTO joinRequestDTO) {
        return roomService.joinRoom(joinRequestDTO);
    }

}
