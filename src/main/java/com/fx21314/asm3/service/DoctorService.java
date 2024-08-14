package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.*;
import com.fx21314.asm3.entity.*;
import com.fx21314.asm3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorUserRepo doctorUserRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ScheduleRepo scheduleRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ClinicRepo clinicRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private ExtrainfoRepo extrainfoRepo;

    public DoctorDTO getDoctorByEmail(String email) {

        var user = userRepo.findByEmail(email).orElseThrow();

        DoctorUser doctor = doctorUserRepository.findByDoctorId(user.getId());
        if (doctor == null) {
            // Xử lý trường hợp không tìm thấy bác sĩ
            return null;
        }

        // Mapping thông tin bác sĩ vào DoctorDTO
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorName(doctor.getDoctor().getName());
        doctorDTO.setSpecialization(doctor.getSpecialization());
        doctorDTO.setIntroduction(doctor.getIntroduction());
        doctorDTO.setTrainingProcess(doctor.getTrainingProcess());
        doctorDTO.setAchievements(doctor.getAchievements());

        // Lấy thông tin về Clinic của bác sĩ
        ClinicDTO clinicDTO = new ClinicDTO();
        Clinic clinic = doctor.getClinic();
        clinicDTO.setAddress(clinic.getAddress());
        clinicDTO.setPhone(clinic.getPhone());
        // Thêm các thông tin khác của Clinic vào clinicDTO

        // Lấy thông tin về lịch khám của bác sĩ
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findByDoctorId(user.getId());
        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setAppointmentDate(schedule.getAppointmentDate());
            scheduleDTO.setAppointmentTime(schedule.getAppointmentTime());
            // Thêm các thông tin khác của lịch khám vào scheduleDTO
            scheduleDTOList.add(scheduleDTO);
        }

        doctorDTO.setClinic(clinicDTO);
        doctorDTO.setSchedules(scheduleDTOList);

        return doctorDTO;
    }

    public List<DoctorDTO> getDoctorsByKeyword(String keyword) {
        List<DoctorUser> doctors = doctorUserRepository.findBySpecializationNameContainingIgnoreCase(keyword);
        List<DoctorDTO> doctorDTOList = new ArrayList<>();

        try {
            if (doctors == null || doctors.isEmpty()) {
                System.out.println("No doctors found for the given keyword");
                return doctorDTOList;
            }

            for (DoctorUser doctor : doctors) {
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setDoctorName(doctor.getDoctor().getName());
                doctorDTO.setSpecialization(doctor.getSpecialization());
                doctorDTO.setIntroduction(doctor.getIntroduction());
                doctorDTO.setTrainingProcess(doctor.getTrainingProcess());
                doctorDTO.setAchievements(doctor.getAchievements());

                ClinicDTO clinicDTO = new ClinicDTO();
                Clinic clinic = doctor.getClinic();
                clinicDTO.setAddress(clinic.getAddress());
                clinicDTO.setPhone(clinic.getPhone());

                doctorDTO.setClinic(clinicDTO);
                doctorDTO.setStatusCode(200);
                doctorDTO.setMessage("Doctor with specialization: " + keyword);

                doctorDTOList.add(doctorDTO);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return doctorDTOList;
    }

    public List<PatientDTO> getPatientsByDoctorEmail(String email) {
        List<PatientDTO> resp = new ArrayList<>();
        User user = userRepo.findByEmail(email).orElseThrow();
        DoctorUser doctorUser = doctorUserRepository.findByDoctorId(user.getId());

        List<Patient> patientList = patientRepo.findByDoctorId(doctorUser.getId());

        if (patientList.isEmpty()) {
            PatientDTO emptyPatientDTO = new PatientDTO();
            emptyPatientDTO.setStatusCode(404);
            emptyPatientDTO.setMessage("No patients found for this doctor.");
            resp.add(emptyPatientDTO);
            return resp;
        }

        for (Patient patient : patientList) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setName(patient.getUser().getName());
            patientDTO.setGender(patient.getUser().getGender());
            patientDTO.setAddress(patient.getUser().getAddress());

            ExtraInfo extrainfo = extrainfoRepo.findByPatientId(patient.getId()).orElseThrow();
            ExtrainforDTO extrainforDTO = new ExtrainforDTO();

            extrainforDTO.setMoreInfo(extrainfo.getMoreInfo());
            extrainforDTO.setHistoryBreath(extrainfo.getHistoryBreath());
            extrainforDTO.setOldForms(extrainfo.getOldForms());

            patientDTO.setExtrainfo(extrainforDTO);
            patientDTO.setMessage("List patient by doctor: " + user.getName());
            patientDTO.setStatusCode(200);
            resp.add(patientDTO);
        }

        return resp;
    }
}

