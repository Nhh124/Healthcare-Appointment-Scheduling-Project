package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.ClinicDTO;
import com.fx21314.asm3.dto.PatientDTO;
import com.fx21314.asm3.dto.ScheduleDTO;
import com.fx21314.asm3.dto.SpecializationDTO;
import com.fx21314.asm3.entity.Clinic;
import com.fx21314.asm3.entity.Patient;
import com.fx21314.asm3.entity.Schedule;
import com.fx21314.asm3.entity.Specialization;
import com.fx21314.asm3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientUserRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ClinicRepo clinicRepo;

    public PatientDTO getPatientByEmail(String email) {
        var user = userRepo.findByEmail(email).orElseThrow();
        PatientDTO patientDTO = new PatientDTO();
        try {
            Patient patient = patientUserRepository.findByUserId(user.getId()).orElseThrow();
            if (patient == null) {
                patientDTO.setStatusCode(404);
                patientDTO.setMessage("Not Found User");
            }

            // Mapping thông tin bệnh nhân vào PatientDTO
            patientDTO.setStatusCode(200);
            patientDTO.setMessage("Patient Information: ");
            patientDTO.setName(user.getName());
            patientDTO.setEmail(user.getEmail());

            // Lấy thông tin về Clinic của bệnh nhân
            ClinicDTO clinicDTO = new ClinicDTO();
            Clinic clinic = clinicRepo.findById(patient.getDoctor().getClinic().getId());
            clinicDTO.setAddress(clinic.getAddress());
            clinicDTO.setPhone(clinic.getPhone());
            // Thêm các thông tin khác của Clinic vào clinicDTO

            // Lấy thông tin về lịch khám của bác sĩ
            List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
            List<Schedule> schedules = scheduleRepo.findByDoctorId(user.getId());
            for (Schedule schedule : schedules) {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                scheduleDTO.setAppointmentDate(schedule.getAppointmentDate());
                scheduleDTO.setAppointmentTime(schedule.getAppointmentTime());
                // Thêm các thông tin khác của lịch khám vào scheduleDTO
                scheduleDTOList.add(scheduleDTO);
            }

            patientDTO.setClinic(clinicDTO);
            patientDTO.setScheduleDTO(scheduleDTOList);
        }catch (Exception e){
            patientDTO.setStatusCode(500);
            patientDTO.setMessage("Error: " + e);
        }

        return patientDTO;
    }

    public void updateUserInfo(PatientDTO updatedPatientDTO) {
        try {
            // Tìm người dùng dựa vào địa chỉ email
            var user = userRepo.findByEmail(updatedPatientDTO.getEmail()).orElseThrow(() -> new Exception("User not found"));

            // Cập nhật thông tin người dùng từ updatedPatientDTO
            user.setName(updatedPatientDTO.getName());
            user.setAddress(updatedPatientDTO.getAddress());
            user.setPhone(updatedPatientDTO.getPhone());
            user.setAvatar(updatedPatientDTO.getAvatar());
            // Các thông tin khác cần cập nhật tương tự

            // Lưu thông tin người dùng đã cập nhật
            userRepo.save(user);

            updatedPatientDTO.setStatusCode(200);
            updatedPatientDTO.setMessage("Update Successfully");
        } catch (Exception e) {
            updatedPatientDTO.setStatusCode(500);
            updatedPatientDTO.setMessage("Error: " + e.getMessage());
        }
    }
}