package com.theo.quixx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/api/hc")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok("Server is alive !");
    }

}
