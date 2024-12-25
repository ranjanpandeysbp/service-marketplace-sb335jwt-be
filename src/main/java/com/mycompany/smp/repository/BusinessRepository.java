package com.mycompany.smp.repository;

import com.mycompany.smp.entity.BusinessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<BusinessTypeEntity, Long> {
    List<BusinessTypeEntity> findAllByOrderByNameAsc();
}
