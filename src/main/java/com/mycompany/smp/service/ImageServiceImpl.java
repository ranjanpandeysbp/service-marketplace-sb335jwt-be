package com.mycompany.smp.service;
import com.mycompany.smp.dto.ImageDTO;
import com.mycompany.smp.entity.BusinessItemEntity;
import com.mycompany.smp.entity.ImageEntity;
import com.mycompany.smp.repository.BusinessItemRepository;
import com.mycompany.smp.repository.ImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ImageServiceImpl {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BusinessItemRepository businessItemRepository;

    public ImageDTO saveImage(ImageDTO image, Long businessItemId) {
        Optional<BusinessItemEntity> optBie = businessItemRepository.findById(businessItemId);
        if(optBie.isPresent()){
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setCreatedAt(LocalDateTime.now());
            imageEntity.setImagePath(image.getImagePath());
            imageEntity.setImageFileName(imageEntity.getImageFileName());
            imageEntity.setBusinessItemEntity(optBie.get());
            imageEntity = imageRepository.save(imageEntity);
            BeanUtils.copyProperties(imageEntity, image);
        }
        return image;
    }

    public ImageDTO create(ImageDTO image) {
        ImageEntity imageEntity = new ImageEntity();
        BeanUtils.copyProperties(image, imageEntity);
        imageEntity = imageRepository.save(imageEntity);
        BeanUtils.copyProperties(imageEntity, image);
        return image;
    }

    public List<ImageDTO> viewAll() {
        List<ImageEntity> imgEntities = (List<ImageEntity>) imageRepository.findAll();
        List<ImageDTO> imgDtos = new ArrayList<>();
        ImageDTO imageDTO = null;
        for(ImageEntity ie: imgEntities){
            imageDTO = new ImageDTO();
            BeanUtils.copyProperties(ie, imageDTO);
            imgDtos.add(imageDTO);
        }
        return imgDtos;
    }

    public ImageDTO viewById(long id) {
        Optional<ImageEntity> ieOpt = imageRepository.findById(id);
        ImageDTO imageDTO = null;
        if(ieOpt.isPresent()){
            imageDTO = new ImageDTO();
            BeanUtils.copyProperties(ieOpt.get(), imageDTO);
        }
        return imageDTO;
    }

    public Long deleteById(long id) {
        imageRepository.deleteById(id);
        return id;
    }
}
