package com.mycompany.smp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessItemDTO {
    private Long id;
    private String title;
    private String description;
    private Double unitPrice;
    private Long businessDetailId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
