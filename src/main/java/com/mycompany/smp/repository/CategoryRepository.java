package com.mycompany.smp.repository;

import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
