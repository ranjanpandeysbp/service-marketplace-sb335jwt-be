package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.service.ServiceDetailServiceImpl;
import com.mycompany.smp.service.ServiceResponseDTO;
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

    @GetMapping("/status/{active}")
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(@PathVariable Boolean active){
        List<ServiceResponseDTO> dtoList = null;
            if(active){
                dtoList = serviceDetailService.getAll();
            }else {
                dtoList = serviceDetailService.getAllInactive();
            }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ServiceResponseDTO>> getAllActiveServicesForCategory(@PathVariable Long categoryId){
        List<ServiceResponseDTO> dtoList = serviceDetailService.getAllActiveByCategory(categoryId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceResponseDTO>> getAllActiveServicesForProvider(@PathVariable Long providerId){
        List<ServiceResponseDTO> dtoList = serviceDetailService.getAllActiveByProvider(providerId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable Long serviceId, @RequestBody ServiceRequestDTO requestDTO){
       ServiceResponseDTO responseDTO = serviceDetailService.update(requestDTO, serviceId);
       return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
