package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.exception.BusinessException;
import com.mycompany.smp.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    //@Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@Valid @RequestBody CategoryEntity category){
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> allCategories(){
        return new ResponseEntity<>(categoryRepository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> getCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(categoryRepository.findById(categoryId).get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(@Valid @RequestBody CategoryEntity category, @PathVariable Long categoryId){
        Optional<CategoryEntity> optCe = categoryRepository.findById(categoryId);
        if(optCe.isPresent()){
            CategoryEntity categoryDb = optCe.get();
            if(category.getName() != null){
                categoryDb.setName(category.getName());
            }
            if(category.getDescription() != null){
                categoryDb.setDescription(category.getDescription());
            }
            categoryDb.setUpdatedAt(LocalDateTime.now());
            categoryRepository.save(categoryDb);
            return new ResponseEntity<>(categoryDb, HttpStatus.OK);
        }else {
            throw new BusinessException(List.of(new ErrorDTO("CAT_NOT_FOUND", "The category to be updated does not exist")));
        }
    }
}
