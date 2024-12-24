package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.entity.BusinessTypeEntity;
import com.mycompany.smp.entity.IndustryTypeEntity;
import com.mycompany.smp.exception.BusinessException;
import com.mycompany.smp.repository.BusinessRepository;
import com.mycompany.smp.repository.IndustryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/businesses")
public class BusinessController {

    //@Autowired
    private final BusinessRepository businessRepository;

    public BusinessController(BusinessRepository businessRepository){
        this.businessRepository = businessRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BusinessTypeEntity> addBusinessType(@Valid @RequestBody BusinessTypeEntity businessType){
        businessType.setCreatedAt(LocalDateTime.now());
        businessType.setUpdatedAt(LocalDateTime.now());
        businessType = businessRepository.save(businessType);
        return new ResponseEntity<>(businessType, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusinessTypeEntity>> allBusinessType(){
        return new ResponseEntity<>(businessRepository.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{businessTypeId}")
    public ResponseEntity<BusinessTypeEntity> updateBusinessType(@Valid @RequestBody BusinessTypeEntity businessType, @PathVariable Long businessTypeId){
        Optional<BusinessTypeEntity> optBt = businessRepository.findById(businessTypeId);
        if(optBt.isPresent()){
            BusinessTypeEntity businessTypeDb = optBt.get();
            if(businessTypeDb.getName() != null){
                businessTypeDb.setName(businessType.getName());
            }
            if(businessTypeDb.getDescription() != null){
                businessTypeDb.setDescription(businessType.getDescription());
            }
            businessTypeDb.setUpdatedAt(LocalDateTime.now());
            businessRepository.save(businessTypeDb);
            return new ResponseEntity<>(businessTypeDb, HttpStatus.OK);
        }else {
            throw new BusinessException(List.of(new ErrorDTO("BT_NOT_FOUND", "The business type to be updated does not exist")));
        }
    }
}
