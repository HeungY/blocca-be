package com.theo.blocca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "firstPlayer")
    private String firstPlayer;

    @Getter
    @Column(name = "secondPlayer")
    private String secondPlayer;

    @Getter
    @Column(name = "isActive")
    private boolean active;

    @Getter
    @Column(name = "code")
    private String code;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Builder
    public Room(String firstPlayer, String code) {
        this.firstPlayer = firstPlayer;
        this.active = true;
        this.code = code;
        this.createdAt = LocalDateTime.now();
    }

    public void join(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public boolean hasSecondPlayer() {
        return secondPlayer != null;
    }

    public void close() {
        this.active = false;
    }
}
