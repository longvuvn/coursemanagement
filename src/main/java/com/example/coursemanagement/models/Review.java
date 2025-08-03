package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.ReviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"review\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends Auditing{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String comment;

    @Min(1)
    @Max(5)
    @NotNull
    private int rating;

    private ReviewStatus status;


    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner learner;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
