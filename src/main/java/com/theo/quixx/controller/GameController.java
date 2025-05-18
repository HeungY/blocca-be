package com.theo.quixx.controller;

import com.theo.quixx.dto.room.CreateRequestDTO;
import com.theo.quixx.dto.room.CreateResponseDTO;
import com.theo.quixx.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final RoomService roomService;

    public GameController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/api/hc")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok("Server is alive !");
    }

    @PostMapping("/api/room/create")
    public CreateResponseDTO createRoom(CreateRequestDTO createRequestDTO) {
        return new CreateResponseDTO(roomService.createRoom(createRequestDTO).getCode());
    }

}
