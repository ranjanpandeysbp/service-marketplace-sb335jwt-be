package com.mycompany.smp.service;

import java.util.List;

public interface ServiceDetails {
    List<ServiceResponseDTO> getAllActiveByProvider(Long providerId);
    List<ServiceResponseDTO> getAllActiveByCategory(Long categoryId);
    List<ServiceResponseDTO> getAllInactive();
}
