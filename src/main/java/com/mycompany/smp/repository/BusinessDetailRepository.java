package com.mycompany.smp.repository;

import com.mycompany.smp.entity.BusinessDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessDetailRepository extends JpaRepository<BusinessDetailEntity, Long> {
    List<BusinessDetailEntity> findAllByProviderId(Long userId);
}
