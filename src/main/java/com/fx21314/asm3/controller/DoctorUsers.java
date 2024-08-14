package com.fx21314.asm3.controller;

import com.fx21314.asm3.dto.DoctorDTO;
import com.fx21314.asm3.dto.PatientDTO;
import com.fx21314.asm3.dto.ScheduleDTO;
import com.fx21314.asm3.service.DoctorService;
import com.fx21314.asm3.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorUsers {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/infor")
    public ResponseEntity<Object> getDoctorInformation(@RequestParam String email) {
        return ResponseEntity.ok().body(doctorService.getDoctorByEmail(email));
    }

    @GetMapping("/list-patient")
    public ResponseEntity<Object> getPatientList(@RequestParam String email) {
        return ResponseEntity.ok().body(doctorService.getPatientsByDoctorEmail(email));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ScheduleDTO> cancelSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO response = scheduleService.cancelSchedule(scheduleDTO.getId(), scheduleDTO.getReason());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/accept")
    public ResponseEntity<ScheduleDTO> acceptSchedule(@RequestParam int scheduleId) {
        ScheduleDTO response = scheduleService.acceptSchedule(scheduleId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
