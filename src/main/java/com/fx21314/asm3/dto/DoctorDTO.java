package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fx21314.asm3.entity.Clinic;
import com.fx21314.asm3.entity.Schedule;
import com.fx21314.asm3.entity.Specialization;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO extends ReqRes {

    private String doctorName;
    private ClinicDTO clinic;
    private Specialization specialization;
    private String introduction;
    private String trainingProcess;
    private String achievements;
    private List<ScheduleDTO> schedules;

}