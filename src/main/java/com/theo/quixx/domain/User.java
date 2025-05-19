package com.theo.quixx.domain;

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

@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Builder
    public User(String nickname) {
        this.nickname = nickname;
        this.createdAt = LocalDateTime.now();
    }
}
