package ru.t1.tracktime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "track_times")
public class TrackTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_async")
    private Boolean isAsync;

    @Column(name = "timestamp")
    private LocalDateTime timeStamp;

    @Column(name = "execution_duration")
    private Long executionDuration;
}
