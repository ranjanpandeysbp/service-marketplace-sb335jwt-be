package com.mycompany.smp.repository;

import com.mycompany.smp.entity.IndustryTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryRepository extends JpaRepository<IndustryTypeEntity, Long> {
    List<IndustryTypeEntity> findAllByOrderByNameAsc();
}
