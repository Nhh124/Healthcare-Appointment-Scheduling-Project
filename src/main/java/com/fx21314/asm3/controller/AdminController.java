package com.fx21314.asm3.controller;

import com.fx21314.asm3.dto.ReqRes;
import com.fx21314.asm3.dto.ScheduleDTO;
import com.fx21314.asm3.repository.ScheduleRepo;
import com.fx21314.asm3.service.AdminService;
import com.fx21314.asm3.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleByPatientId(@PathVariable int patientId) {
        return ResponseEntity.ok(scheduleService.findSchedulesByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleByDoctorId(@PathVariable int doctorId) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDoctorId(doctorId));
    }

    @PostMapping("/lockAccount")
    public ResponseEntity<ReqRes> lockAccount(@RequestBody ReqRes lockAccountRequest) {
        return  ResponseEntity.ok(adminService.lockAccount(lockAccountRequest));
    }

    @PostMapping("/unlockAccount")
    public ResponseEntity<ReqRes> unlockAccount(@RequestBody ReqRes unlockAccountRequest) {
        return  ResponseEntity.ok(adminService.unlockAccount(unlockAccountRequest));
    }

}
