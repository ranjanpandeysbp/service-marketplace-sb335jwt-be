package com.mycompany.smp.repository;

import com.mycompany.smp.entity.BusinessItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessItemRepository extends JpaRepository<BusinessItemEntity, Long> {

}
