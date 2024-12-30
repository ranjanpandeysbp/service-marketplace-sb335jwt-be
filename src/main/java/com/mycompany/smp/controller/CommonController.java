package com.mycompany.smp.controller;

import com.mycompany.smp.dto.BusinessItemDTO;
import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.dto.BusinessResponseDTO;
import com.mycompany.smp.entity.BusinessTypeEntity;
import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.entity.IndustryTypeEntity;
import com.mycompany.smp.repository.BusinessRepository;
import com.mycompany.smp.repository.CategoryRepository;
import com.mycompany.smp.repository.IndustryRepository;
import com.mycompany.smp.service.NoAuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private NoAuthServiceImpl noAuthService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> allCategories(){
        return new ResponseEntity<>(categoryRepository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/industries")
    public ResponseEntity<List<IndustryTypeEntity>> allIndustriesType(){
        return new ResponseEntity<>(industryRepository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/businesses")
    public ResponseEntity<List<BusinessTypeEntity>> allBusinessType(){
        return new ResponseEntity<>(businessRepository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @PostMapping("/onboard/business")
    public ResponseEntity<BusinessResponseDTO> addService(@Valid @RequestBody BusinessRequestDTO serviceRequestDTO){
        BusinessResponseDTO serviceResponseDTO = noAuthService.onboardBusiness(serviceRequestDTO);
        return new ResponseEntity<>(serviceResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/onboard/businessItem")
    public ResponseEntity<Long> addBusinessItem(@Valid @RequestBody BusinessItemDTO businessItemDTO){
        Long businessItemId = noAuthService.addBusinessItem(businessItemDTO);
        return new ResponseEntity<>(businessItemId, HttpStatus.CREATED);
    }
}
