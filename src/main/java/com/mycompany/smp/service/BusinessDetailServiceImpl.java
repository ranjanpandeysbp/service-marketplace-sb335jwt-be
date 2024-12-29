package com.mycompany.smp.service;

import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.dto.ErrorDTO;
import com.mycompany.smp.dto.BusinessResponseDTO;
import com.mycompany.smp.entity.BusinessDetailEntity;
import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.entity.UserEntity;
import com.mycompany.smp.exception.BusinessException;
import com.mycompany.smp.mapper.BusinessDetailMapper;
import com.mycompany.smp.repository.CategoryRepository;
import com.mycompany.smp.repository.BusinessDetailRepository;
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
public class BusinessDetailServiceImpl implements CommonService<BusinessResponseDTO, BusinessRequestDTO> {

    @Autowired
    private BusinessDetailRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BusinessResponseDTO add(BusinessRequestDTO request) {

//        ServiceEntity se = new ServiceEntity();
//        BeanUtils.copyProperties(request, se);
//        se.setCreatedAt(LocalDateTime.now());
//        se.setUpdatedAt(LocalDateTime.now());
//        se.setActive(true);

        BusinessDetailEntity se = BusinessDetailMapper.INSTANCE.toEntity(request);
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        Optional<UserEntity> optUe = userRepository.findById(userDetails.getId());
        BusinessResponseDTO sr = null;
        if(optUe.isPresent()){
            se.setProvider(optUe.get());
            Optional<CategoryEntity> optCe = categoryRepository.findById(request.getCategoryId());
            if(optCe.isPresent()){
                se.setCategory(optCe.get());
            }
            se = serviceRepository.save(se);
            sr = new BusinessResponseDTO();
            BeanUtils.copyProperties(se, sr);
        }
        return sr;
    }

    @Override
    public BusinessResponseDTO update(BusinessRequestDTO request, Long id) {
        Optional<BusinessDetailEntity> optSe = serviceRepository.findById(id);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        BusinessDetailEntity se = optSe.get();
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
        return BusinessDetailMapper.INSTANCE.toDto(se);
    }

    @Override
    public BusinessResponseDTO delete(Long id) {
        return null;
    }

    @Override
    public BusinessResponseDTO get(Long id) {
        Optional<BusinessDetailEntity> optSe = serviceRepository.findById(id);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        return BusinessDetailMapper.INSTANCE.toDto(optSe.get());
    }

    @Override
    public List<BusinessResponseDTO> getAll(Long providerId) {
        //UserDetailsImpl userDetails = commonUtil.loggedInUser();
        List<BusinessDetailEntity> seList = serviceRepository.findAllByProviderId(providerId);
        //return seList.stream().map((se)-> ServiceDetailMapper.INSTANCE.toDto(se)).toList();
        return seList.stream().map(BusinessDetailMapper.INSTANCE::toDto).toList();
    }

    @Override
    public List<BusinessResponseDTO> search(BusinessRequestDTO request) {
        return List.of();
    }

    public BusinessResponseDTO updateStatus(BusinessRequestDTO request, Long serviceId){
        Optional<BusinessDetailEntity> optSe = serviceRepository.findById(serviceId);
        optSe.orElseThrow(
                () -> new BusinessException(
                        List.of(new ErrorDTO("SERVICE_NOT_FOUND", "The service to be updated does not exist"))
                ));
        BusinessDetailEntity se = optSe.get();
        if(Objects.nonNull(request.isActive())){
            se.setActive(request.isActive());
        }
        se.setUpdatedAt(LocalDateTime.now());
        se = serviceRepository.save(se);
        return BusinessDetailMapper.INSTANCE.toDto(se);
    }
}
