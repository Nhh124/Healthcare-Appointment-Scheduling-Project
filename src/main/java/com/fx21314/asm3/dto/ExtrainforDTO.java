package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fx21314.asm3.entity.Patient;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtrainforDTO {

    private Patient patient;
    private String historyBreath;
    private String oldForms;
    private String sendForms;
    private String moreInfo;

}
