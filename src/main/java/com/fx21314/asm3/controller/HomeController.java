package com.fx21314.asm3.controller;

import com.fx21314.asm3.dto.ClinicDTO;
import com.fx21314.asm3.dto.ClinicSpecializationDTO;
import com.fx21314.asm3.dto.DoctorDTO;
import com.fx21314.asm3.dto.SpecializationDTO;
import com.fx21314.asm3.service.ClinicService;
import com.fx21314.asm3.service.DoctorService;
import com.fx21314.asm3.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class HomeController {
    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/specializationsByRate")
    public ResponseEntity<List<SpecializationDTO>> findSpecializationsByRate() {
        List<SpecializationDTO> specializationDTOList = specializationService.findSpecializationsByRate();
        if (specializationDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(specializationDTOList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(specializationDTOList);
    }

    @GetMapping("/clinicsByRate")
    public ResponseEntity<List<ClinicDTO>> findSpClinicByRate() {
        List<ClinicDTO> clinicDTOS = clinicService.findClinicsByRate();
        if (clinicDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clinicDTOS);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clinicDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClinicSpecializationDTO>> findInformationByKeyword(@RequestParam String keyword){
        return ResponseEntity.ok(clinicService.findByKeyword(keyword));
    }

    @GetMapping("/searchdoctor")
    public ResponseEntity<List<DoctorDTO>> findSpecByKeyword(@RequestParam String keyword){
        return ResponseEntity.ok(doctorService.getDoctorsByKeyword(keyword));
    }
}
