package com.example.coursemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;


@Entity
@Table(name = "\"learner\"")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Learner extends User {


    @Column(nullable = false)
    private int totalCourses;

    private String level;

    @OneToMany(mappedBy = "learner", fetch = FetchType.LAZY)
    private Set<Review> review;

    @OneToMany(mappedBy = "learner", fetch = FetchType.LAZY)
    private Set<Submission> submisstion;

    @OneToMany(mappedBy = "learner", fetch = FetchType.LAZY)
    private Set<Order> order;

    @OneToMany(mappedBy = "learner", fetch = FetchType.LAZY)
    private Set<Registration> registration;
}
