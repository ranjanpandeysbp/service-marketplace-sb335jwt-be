package com.mycompany.smp.controller;

import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.dto.BusinessResponseDTO;
import com.mycompany.smp.service.BusinessDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/services")
public class BusinessDetailController {

    private BusinessDetailServiceImpl serviceDetailService;

    public BusinessDetailController(BusinessDetailServiceImpl serviceDetailService){
        this.serviceDetailService = serviceDetailService;
    }

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BusinessResponseDTO> addService(@Valid @RequestBody BusinessRequestDTO serviceRequestDTO){
        BusinessResponseDTO serviceResponseDTO = serviceDetailService.add(serviceRequestDTO);
        return new ResponseEntity<>(serviceResponseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BusinessResponseDTO>> getAll(@PathVariable Long userId){
        return new ResponseEntity<>(serviceDetailService.getAll(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
    @GetMapping("/{serviceId}")
    public ResponseEntity<BusinessResponseDTO> get(@PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.get(serviceId), HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<BusinessResponseDTO> updateService(@Valid @RequestBody BusinessRequestDTO serviceRequestDTO, @PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.update(serviceRequestDTO, serviceId), HttpStatus.OK);
    }

    @PatchMapping("/{serviceId}")
    public ResponseEntity<BusinessResponseDTO> updateServiceStatus(@Valid @RequestBody BusinessRequestDTO serviceRequestDTO, @PathVariable Long serviceId){
        return new ResponseEntity<>(serviceDetailService.updateStatus(serviceRequestDTO, serviceId), HttpStatus.OK);
    }
}
