package com.mycompany.smp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessRequestDTO {

    //@NotNull
    private String title;
    private String description;
    // @NotNull
    private String mobile;
    private String altNumber;
    //@NotNull
    private Long categoryId;
    private Long industryTypeId;
    private Long businessTypeId;
    // @NotNull
    private String email1;
    private String email2;
    //@NotNull
    private String addressLine;
    private String street;
    //@NotNull
    private String city;
    private String district;
    //@NotNull
    private String pinCode;
    //@NotNull
    private String state;
    private String country;
    private String googleEmbed;
    private String lat;
    private String lng;
    private String operationTiming;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Boolean isActive() {
        return active;
    }

}
