package com.mycompany.smp.repository;

import com.mycompany.smp.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {
     List<SubCategoryEntity> findAllByCategoryIdOrderByNameAsc(Long categoryId);
}
