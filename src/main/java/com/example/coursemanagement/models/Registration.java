package com.example.coursemanagement.models;


import com.example.coursemanagement.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"registration\"")
@Data
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private Instant RegisteredAt;

    private RegistrationStatus status;
}
