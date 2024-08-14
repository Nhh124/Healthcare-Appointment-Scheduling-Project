package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicDTO extends ReqRes{
    private String address;
    private String phone;
    private String introductionHTML;
    private String introductionMarkDown;
    private String description;
    private String image;
    private String workingHours;
    private String importantNotes;
    private BigDecimal consultationFee;
    private LocalDateTime createdAt;
    private int rate;

}
