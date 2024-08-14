package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO extends ReqRes {
    private DoctorDTO doctor;
    private ClinicDTO clinic;
    private List<ScheduleDTO> scheduleDTO;
    private ExtrainforDTO extrainfo;
}
