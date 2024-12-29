package com.mycompany.smp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "business_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Double unitPrice;
    @ManyToOne
    private BusinessDetailEntity businessEntity;
    @OneToMany(mappedBy = "businessItemEntity")
    private List<ImageEntity> images;
}
