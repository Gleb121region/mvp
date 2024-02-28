package ru.spbstu.mvp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", nullable = false, updatable = false)
    private Integer id;

    @NotBlank
    @Column(name = "photo_url", nullable = false, updatable = false)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false, updatable = false)
    private Announcement announcement;
}
