package com.mycompany.smp.repository;

import com.mycompany.smp.entity.BusinessItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessItemRepository extends JpaRepository<BusinessItemEntity, Long> {
    List<BusinessItemEntity> findAllByBusinessDetailEntityIdOrderByUpdatedAtDesc(Long businessDetailEntityId);
}
