package com.theo.quixx.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "room")
public final class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstPlayer")
    private String firstPlayer;

    @Column(name = "secondPlayer")
    private String secondPlayer;

    @Column(name = "isActive")
    private boolean active;

    @Column(name = "code")
    private String code;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Builder
    public Room(String firstPlayer, String code) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = "undefined";
        this.active = true;
        this.code = code;
        this.createdAt = LocalDateTime.now();
    }

    public void changeSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String nameFirstPlayer(String firstPlayer) {
        return firstPlayer;
    }

    public String nameSecondPlayer(String secondPlayer) {
        return secondPlayer;
    }

    public boolean roomIsActive() {
        return active;
    }

    public String roomCode() {
        return code;
    }

    public void roomClose() {
        this.active = false;
    }
}
