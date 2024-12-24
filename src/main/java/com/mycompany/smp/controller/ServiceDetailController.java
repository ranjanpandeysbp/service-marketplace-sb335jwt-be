package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.service.ServiceDetailServiceImpl;
import com.mycompany.smp.dto.ServiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceResponseDTO>> getAll(@PathVariable Long userId){
        return new ResponseEntity<>(serviceDetailService.getAll(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> get(@PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.get(serviceId), HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateService(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO, @PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.update(serviceRequestDTO, serviceId), HttpStatus.OK);
    }

    @PatchMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateServiceStatus(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO, @PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.updateStatus(serviceRequestDTO, serviceId), HttpStatus.OK);
    }
}
