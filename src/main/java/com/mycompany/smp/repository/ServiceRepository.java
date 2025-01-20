package com.mycompany.smp.repository;

import com.mycompany.smp.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findAllByActiveTrue();
    List<ServiceEntity> findAllByActiveTrueAndCategoryId(Long categoryId);
    List<ServiceEntity> findAllByActiveTrueAndProviderId(Long providerId);
    Long countByActiveTrue();
    List<ServiceEntity> findAllByActiveFalse();
}

//public interface EventRepository extends JpaRepository<EventEntity, Long> {
//    Long countByIdAndOwnerRolesName(Long eventId, ERole role);
//    Long countByIsCancelledFalse();
//    Long countByIdAndIsExpiredFalseAndIsCancelledFalseAndEventEndDtGreaterThanEqual(Long eventId, LocalDate localDate);
//    List<EventEntity> findAllByOwnerId(Long userId);
//    Page<EventEntity> findAllByIsExpiredFalseAndIsCancelledFalseAndEventEndDtGreaterThanEqualOrderByEventStartDtAsc(LocalDate today, Pageable pageable);
//    List<EventEntity> findByEventCityIdAndIsExpiredFalseAndIsCancelledFalseAndEventEndDtGreaterThanEqual(Long eventCityId, LocalDate today);
//    List<EventEntity> findByIsExpiredFalseAndIsCancelledFalseAndEventEndDtBetween(LocalDate startDt, LocalDate endDt);
//    List<EventEntity> findByEventStateIdAndIsExpiredFalseAndIsCancelledFalseAndEventEndDtGreaterThanEqual(Long eventStateId, LocalDate today);
//    List<EventEntity> findByIsExpiredFalseAndIsCancelledFalseAndEventEndDtGreaterThanEqual(LocalDate today);
//}