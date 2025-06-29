package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.SubmisstionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"submission\"")
@Data
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Float  score;
    private String file_Url;
    private Instant SubmittedAt;
    private SubmisstionStatus status;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
