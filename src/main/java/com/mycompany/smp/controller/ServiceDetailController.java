package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.service.ServiceDetailServiceImpl;
import com.mycompany.smp.service.ServiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/services")
public class ServiceDetailController {

    private ServiceDetailServiceImpl serviceDetailService;

    public ServiceDetailController(ServiceDetailServiceImpl serviceDetailService){
        this.serviceDetailService = serviceDetailService;
    }

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceResponseDTO> addService(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO){
        ServiceResponseDTO serviceResponseDTO = serviceDetailService.add(serviceRequestDTO);
        return new ResponseEntity<>(serviceResponseDTO, HttpStatus.CREATED);
    }

}
