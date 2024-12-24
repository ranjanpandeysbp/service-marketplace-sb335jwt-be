package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.entity.IndustryTypeEntity;
import com.mycompany.smp.exception.BusinessException;
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
@RequestMapping("/api/v1/industries")
public class IndustryController {

    //@Autowired
    private final IndustryRepository industryRepository;

    public IndustryController(IndustryRepository industryRepository){
        this.industryRepository = industryRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<IndustryTypeEntity> addIndustryType(@Valid @RequestBody IndustryTypeEntity industryType){
        industryType.setCreatedAt(LocalDateTime.now());
        industryType.setUpdatedAt(LocalDateTime.now());
        industryType = industryRepository.save(industryType);
        return new ResponseEntity<>(industryType, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IndustryTypeEntity>> allIndustriesType(){
        return new ResponseEntity<>(industryRepository.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{industryTypeId}")
    public ResponseEntity<IndustryTypeEntity> updateIndustryType(@Valid @RequestBody IndustryTypeEntity industryType, @PathVariable Long industryTypeId){
        Optional<IndustryTypeEntity> optIt = industryRepository.findById(industryTypeId);
        if(optIt.isPresent()){
            IndustryTypeEntity industryTypeDb = optIt.get();
            if(industryType.getName() != null){
                industryTypeDb.setName(industryType.getName());
            }
            if(industryType.getDescription() != null){
                industryTypeDb.setDescription(industryType.getDescription());
            }
            industryTypeDb.setUpdatedAt(LocalDateTime.now());
            industryRepository.save(industryTypeDb);
            return new ResponseEntity<>(industryTypeDb, HttpStatus.OK);
        }else {
            throw new BusinessException(List.of(new ErrorDTO("IT_NOT_FOUND", "The industry type to be updated does not exist")));
        }
    }
}
