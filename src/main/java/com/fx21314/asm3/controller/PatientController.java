package com.fx21314.asm3.controller;

import com.fx21314.asm3.dto.DoctorDTO;
import com.fx21314.asm3.dto.ScheduleDTO;
import com.fx21314.asm3.repository.PatientRepo;
import com.fx21314.asm3.service.PatientService;
import com.fx21314.asm3.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/infor")
    public ResponseEntity<Object> getPatientInformation(@RequestParam String email) {
        return ResponseEntity.ok().body(patientService.getPatientByEmail(email));
    }

    @PostMapping("/add-schedule")
    public ResponseEntity<Object> addSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO response = scheduleService.createSchedule(scheduleDTO);

        if (response.getStatusCode() == 200) {
            return ResponseEntity.ok().body("Schedule added successfully");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getError());
        }
    }
    }
