package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.ChapterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"chapter\"")
@Data
public class Chapter extends Auditing{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String title;

    private ChapterStatus status;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
    private Set<Lesson> lesson;
}
