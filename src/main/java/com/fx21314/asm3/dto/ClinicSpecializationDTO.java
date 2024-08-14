package com.fx21314.asm3.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fx21314.asm3.entity.Clinic;
import com.fx21314.asm3.entity.Specialization;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicSpecializationDTO extends ReqRes {
    private Specialization specializations;
    private Clinic clinic;

}
