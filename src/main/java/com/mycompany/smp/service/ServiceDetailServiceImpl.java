package com.mycompany.smp.service;

import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.dto.UpdateServiceRequestDTO;
import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.entity.ServiceEntity;
import com.mycompany.smp.entity.UserEntity;
import com.mycompany.smp.exception.BusinessException;
import com.mycompany.smp.mapper.ServiceDetailMapper;
import com.mycompany.smp.repository.CategoryRepository;
import com.mycompany.smp.repository.ServiceRepository;
import com.mycompany.smp.repository.UserRepository;
import com.mycompany.smp.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceDetailServiceImpl implements CommonService<ServiceResponseDTO ,ServiceRequestDTO> {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ServiceResponseDTO add(ServiceRequestDTO request) {

//        ServiceEntity se = new ServiceEntity();
//        BeanUtils.copyProperties(request, se);
//        se.setCreatedAt(LocalDateTime.now());
//        se.setUpdatedAt(LocalDateTime.now());
//        se.setActive(true);

        ServiceEntity se = ServiceDetailMapper.INSTANCE.toEntity(request);
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        Optional<UserEntity> optUe = userRepository.findById(userDetails.getId());
        ServiceResponseDTO sr = null;
        if(optUe.isPresent()){
            se.setProvider(optUe.get());
            Optional<CategoryEntity> optCe = categoryRepository.findById(request.getCategoryId());
            if(optCe.isPresent()){
                se.setCategory(optCe.get());
            }
            se = serviceRepository.save(se);
            sr = new ServiceResponseDTO();
            BeanUtils.copyProperties(se, sr);
        }
        return sr;
    }

    @Override
    public ServiceResponseDTO update(ServiceRequestDTO request, Long id) {
        Optional<ServiceEntity> optSe = serviceRepository.findById(id);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        ServiceEntity se = optSe.get();
        if(Objects.nonNull(request.getOperationTiming())){
            se.setOperationTiming(request.getOperationTiming());
        }
        if(Objects.nonNull(request.getTitle())){
            se.setTitle(request.getTitle());
        }
        if(Objects.nonNull(request.getDescription())){
            se.setDescription(request.getDescription());
        }
        se.setUpdatedAt(LocalDateTime.now());
        se = serviceRepository.save(se);
        return ServiceDetailMapper.INSTANCE.toDto(se);
    }

    @Override
    public ServiceResponseDTO delete(Long id) {
        return null;
    }

    @Override
    public ServiceResponseDTO get(Long id) {
        Optional<ServiceEntity> optSe = serviceRepository.findById(id);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        return ServiceDetailMapper.INSTANCE.toDto(optSe.get());
    }

    @Override
    public List<ServiceResponseDTO> getAll(Long providerId) {
        //UserDetailsImpl userDetails = commonUtil.loggedInUser();
        List<ServiceEntity> seList = serviceRepository.findAllByProviderId(providerId);
        //return seList.stream().map((se)-> ServiceDetailMapper.INSTANCE.toDto(se)).toList();
        return seList.stream().map(ServiceDetailMapper.INSTANCE::toDto).toList();
    }

    @Override
    public List<ServiceResponseDTO> search(ServiceRequestDTO request) {
        return List.of();
    }

    public ServiceResponseDTO updateStatus(ServiceRequestDTO request, Long serviceId){
        Optional<ServiceEntity> optSe = serviceRepository.findById(serviceId);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        ServiceEntity se = optSe.get();
        if(Objects.nonNull(request.isActive())){
            se.setActive(request.isActive());
        }
        se.setUpdatedAt(LocalDateTime.now());
        se = serviceRepository.save(se);
        return ServiceDetailMapper.INSTANCE.toDto(se);
    }
}
