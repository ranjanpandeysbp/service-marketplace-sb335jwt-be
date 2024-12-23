package com.mycompany.smp.service;

import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.entity.CategoryEntity;
import com.mycompany.smp.entity.ServiceEntity;
import com.mycompany.smp.entity.UserEntity;
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
        return null;
    }

    @Override
    public ServiceResponseDTO delete(Long id) {
        return null;
    }

    @Override
    public ServiceResponseDTO get(Long id) {
        return null;
    }

    @Override
    public List<ServiceResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public List<ServiceResponseDTO> search(ServiceRequestDTO request) {
        return List.of();
    }
}
