package com.mycompany.smp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String phone1;
    private String phone2;
    private String email1;
    private String email2;
    private String addressLine;
    private String street;
    private String city;
    private String district;
    private String pinCode;
    private String state;
    private String country;
    private String image;
    private String lat;
    private String lng;
    private String operationTiming;
    private Boolean active;
    @ManyToOne
    private UserEntity provider;
    @ManyToOne
    private CategoryEntity category;
    @ManyToOne
    private BusinessTypeEntity businessType;
    @ManyToOne
    private IndustryTypeEntity industryType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
