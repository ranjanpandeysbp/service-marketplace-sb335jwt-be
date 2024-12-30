package com.mycompany.smp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private Blob image;
    private String imagePath;
    private String imageFileName;
    private LocalDateTime createdAt;
    @ManyToOne
    private BusinessItemEntity businessItemEntity;
}
