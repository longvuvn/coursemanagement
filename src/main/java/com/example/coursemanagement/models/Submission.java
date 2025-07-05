package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.SubmisstionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"submission\"")
@Data
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Float score;

    @NotBlank(message = "Không được để trống")
    @Column(nullable = false)
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
