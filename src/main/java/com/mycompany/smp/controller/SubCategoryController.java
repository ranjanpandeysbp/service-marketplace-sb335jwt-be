package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.entity.SubCategoryEntity;
import com.mycompany.smp.exception.BusinessException;
import com.mycompany.smp.repository.SubCategoryRepository;
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
@RequestMapping("/api/v1/subcategories")
public class SubCategoryController {

    //@Autowired
    private SubCategoryRepository subCategoryRepository;

    public SubCategoryController(SubCategoryRepository subCategoryRepository){
        this.subCategoryRepository = subCategoryRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SubCategoryEntity> addSubCategory(@Valid @RequestBody SubCategoryEntity category){
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category = subCategoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}/all")
    public ResponseEntity<List<SubCategoryEntity>> allSubCategories(@PathVariable Long categoryId){
        return new ResponseEntity<>(subCategoryRepository.findAllByCategoryIdOrderByNameAsc(categoryId), HttpStatus.OK);
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<SubCategoryEntity> getSubCategory(@PathVariable Long subCategoryId){
        return new ResponseEntity<>(subCategoryRepository.findById(subCategoryId).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<Long> deleteSubCategory(@PathVariable Long subCategoryId){
        subCategoryRepository.deleteById(subCategoryId);
        return new ResponseEntity<>(subCategoryId, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{subCategoryId}")
    public ResponseEntity<SubCategoryEntity> updateSubCategory(@Valid @RequestBody SubCategoryEntity category, @PathVariable Long subCategoryId){
        Optional<SubCategoryEntity> optCe = subCategoryRepository.findById(subCategoryId);
        if(optCe.isPresent()){
            SubCategoryEntity categoryDb = optCe.get();
            if(category.getName() != null){
                categoryDb.setName(category.getName());
            }
            if(category.getDescription() != null){
                categoryDb.setDescription(category.getDescription());
            }
            categoryDb.setUpdatedAt(LocalDateTime.now());
            subCategoryRepository.save(categoryDb);
            return new ResponseEntity<>(categoryDb, HttpStatus.OK);
        }else {
            throw new BusinessException(List.of(new ErrorDTO("SUB_CAT_NOT_FOUND", "The sub category to be updated does not exist")));
        }
    }
}
