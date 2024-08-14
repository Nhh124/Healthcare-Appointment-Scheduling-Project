package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fx21314.asm3.entity.DoctorUser;
import com.fx21314.asm3.entity.Patient;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ScheduleDTO extends ReqRes{

    private int id;
    private DoctorUser doctorUser;
    private Patient patient;
    private String doctorName;
    private String patientName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date appointmentDate;

    private String appointmentTime;
    private String reasonForVisit;
    private double consultationFee;
    private String reason;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}
