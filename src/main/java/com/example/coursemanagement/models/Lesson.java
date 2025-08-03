package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.LessonStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"lesson\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Lesson extends Auditing{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String title;

    private LessonStatus status;

    @NotBlank(message = "Không được để trống")
    @Column(nullable = false)
    private String referenceLink;

    private String content;


    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private Set<Submission> submisstion;
}
