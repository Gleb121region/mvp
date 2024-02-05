package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.spbstu.mvp.entity.enums.FeedbackType;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false, updatable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Column
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column
    @Builder.Default
    private OffsetDateTime updatedAt = null;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Flat flat;
}
