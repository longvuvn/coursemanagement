package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.LessonStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"lesson\"")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String title;

    private LessonStatus status;
    private String referenceLink;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private Set<Submission> submisstion;
}
