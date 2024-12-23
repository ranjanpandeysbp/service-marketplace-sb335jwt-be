package com.mycompany.smp.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String phone1;
    private String phone2;
    private String email1;
    private String email2;
    private String addressLine;
    private String street;
    private String city;
    private String district;
    private String pinCode;
    private String state;
    private String country;
    private String lat;
    private String lng;
    private String operationTiming;
}
