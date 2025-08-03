package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.CourseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"course\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Course extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String title;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá không được âm")
    @Column(nullable = false)
    private BigDecimal price;

    private CourseStatus status;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    @NotBlank(message = "Không được để trống")
    private String description;

    private String image;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Integer totalReviews;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Review> review;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Chapter> chapter;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetail;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Registration> registration;
}
