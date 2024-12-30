package com.mycompany.smp.controller;

import com.mycompany.smp.dto.ImageDTO;
import com.mycompany.smp.service.ImageServiceImpl;
import com.mycompany.smp.service.S3FileuploadServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageController {

    @Value("${bms.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private ImageServiceImpl imageService;
    @Autowired
    private S3FileuploadServiceImpl s3FileuploadService;
    @Autowired
    private ResourceLoader resourceLoader;

    // display image
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayImage(@PathVariable("id") long id) throws IOException, SQLException
    {
        ImageDTO image = imageService.viewById(id);
        byte [] imageBytes = null;

        if(Objects.nonNull(image)) {
            imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        }else{
            Resource resource = resourceLoader.getResource("classpath:defaultImg.jpg");
            File file = resource.getFile();
            imageBytes = new byte[(int)file.length()];
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteImage(@PathVariable("id") long id) throws IOException, SQLException
    {
        Long imageId = imageService.deleteById(id);
        ResponseEntity<Long> re = new ResponseEntity<>(imageId, HttpStatus.NO_CONTENT);
        return re;
    }

    // add image - post
    @PostMapping("/add")
    public ResponseEntity<ImageDTO> addImagePost(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        ImageDTO image = new ImageDTO();
        image.setCreatedAt(LocalDateTime.now());
        image.setImage(blob);
        image = imageService.create(image);
        image.setImage(null);
        ResponseEntity<ImageDTO> re = new ResponseEntity<>(image, HttpStatus.CREATED);
        return re;
    }

    @PostMapping("/add/new")
    public ResponseEntity<ImageDTO> addImageNew(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {

        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            s3FileuploadService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        ImageDTO image = new ImageDTO();
        image.setImagePath(path);
        image.setImageFileName(fileName);
        image.setCreatedAt(LocalDateTime.now());
        image = imageService.create(image);
        image.setImage(null);
        ResponseEntity<ImageDTO> re = new ResponseEntity<>(image, HttpStatus.CREATED);
        return re;
    }

    @PostMapping("/addall/new/{businessItemId}")
    public ResponseEntity<String> addImageNew(HttpServletRequest request, @PathVariable Long businessItemId, @RequestParam("images") MultipartFile[] files) throws IOException, SerialException, SQLException
    {
        //check if the file is empty
        if (files.length == 0) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        for(MultipartFile file: files){
            //Check if the file is an image
            if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                    IMAGE_BMP.getMimeType(),
                    IMAGE_GIF.getMimeType(),
                    IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("FIle uploaded is not an image");
            }
            //get file metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));
            //Save Image in S3 and then save Todo in the database
            String path = String.format("%s/%s", bucketName, UUID.randomUUID());
            String fileName = String.format("%s", file.getOriginalFilename());
            try {
                s3FileuploadService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }
            ImageDTO image = new ImageDTO();
            image.setImagePath(path);
            image.setImageFileName(fileName);
            image.setCreatedAt(LocalDateTime.now());
            image = imageService.saveImage(image, businessItemId);
            image.setImage(null);
        }
        return new ResponseEntity<>("All files uploaded successfully", HttpStatus.CREATED);
    }

    @GetMapping("/display/new/{id}")
    public ResponseEntity<byte[]> displayNewImage(@PathVariable("id") long id) throws IOException, SQLException
    {
        ImageDTO image = imageService.viewById(id);
        if(Objects.isNull(image)){
            image = imageService.viewById(1);
        }
        byte[] imageBytes = s3FileuploadService.download(image.getImagePath(), image.getImageFileName());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
