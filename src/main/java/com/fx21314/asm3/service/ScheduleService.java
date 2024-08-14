package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.ScheduleDTO;
import com.fx21314.asm3.entity.DoctorUser;
import com.fx21314.asm3.entity.Patient;
import com.fx21314.asm3.entity.Schedule;
import com.fx21314.asm3.entity.Status;
import com.fx21314.asm3.repository.DoctorUserRepo;
import com.fx21314.asm3.repository.PatientRepo;
import com.fx21314.asm3.repository.ScheduleRepo;
import com.fx21314.asm3.repository.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepo scheduleRepo;

    @Autowired
    DoctorUserRepo doctorUserRepo;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    StatusRepo statusRepo;


    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        ScheduleDTO resp = new ScheduleDTO();
        try {
            DoctorUser user = doctorUserRepo.findByNameContaining(scheduleDTO.getDoctorName());
            if (user == null) {
                resp.setMessage("Doctor not found with name: " + user.getDoctor().getName());
                resp.setStatusCode(404);
            }

            Patient patient = patientRepo.findByNameContaining(scheduleDTO.getPatientName()).orElse(null);
            if (patient == null) {
                resp.setMessage("Patient not found with name: " + patient.getUser().getName());
                resp.setStatusCode(404);
            }

            Schedule schedule = new Schedule();
            Status status = statusRepo.findById(1).orElseThrow();
            schedule.setStatus(status);
            schedule.setDoctor(user);
            schedule.setPatient(patient);
            schedule.setAppointmentDate(scheduleDTO.getAppointmentDate());
            schedule.setAppointmentTime(scheduleDTO.getAppointmentTime());
            schedule.setReasonForVisit(scheduleDTO.getReasonForVisit());
            schedule.setConsultationFee(scheduleDTO.getConsultationFee());
            Date now = new Date();
            schedule.setCreatedAt(now);

            Schedule savedSchedule = scheduleRepo.save(schedule);

            if (savedSchedule != null && savedSchedule.getId() > 0) {
                resp.setMessage("Schedule Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public ScheduleDTO cancelSchedule(int scheduleId, String reason) {
        ScheduleDTO resp = new ScheduleDTO();
        try {
            Schedule schedule = scheduleRepo.findById(scheduleId).orElse(null);
            if (schedule == null) {
                resp.setMessage("Schedule not found with ID: " + scheduleId);
                resp.setStatusCode(404);
                return resp;
            }

            // Cập nhật trạng thái của lịch
            Status status = statusRepo.findById(4).orElseThrow();
            schedule.setStatus(status);

            // Cập nhật lý do hủy
            schedule.setReason(reason);

            scheduleRepo.save(schedule);

            resp.setMessage("Schedule Cancelled Successfully");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ScheduleDTO acceptSchedule(int scheduleId) {
        ScheduleDTO resp = new ScheduleDTO();
        try {
            Schedule schedule = scheduleRepo.findById(scheduleId).orElse(null);
            if (schedule == null) {
                resp.setMessage("Schedule not found with ID: " + scheduleId);
                resp.setStatusCode(404);
                return resp;
            }


            Status status = statusRepo.findById(2).orElseThrow();
            schedule.setStatus(status);
            scheduleRepo.save(schedule);

            resp.setMessage("Schedule Accepted Successfully");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public List<ScheduleDTO> findSchedulesByDoctorId(int doctorId) {
        List<Schedule> schedules = scheduleRepo.findByDoctorId(doctorId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        ScheduleDTO scheduleDTO = new ScheduleDTO();


        try {
            for (Schedule schedule : schedules) {
                scheduleDTO.setMessage("List of doctor id: " + doctorId);
                scheduleDTO.setId(schedule.getId());
                scheduleDTO.setDoctorName(schedule.getDoctor().getDoctor().getName());
                scheduleDTO.setPatientName(schedule.getPatient().getUser().getName());
                scheduleDTO.setAppointmentDate(schedule.getAppointmentDate());

                scheduleDTO.setStatusCode(200);
                scheduleDTOs.add(scheduleDTO);
            }
        } catch (Exception e){
            scheduleDTO.setMessage("An Error: " + e);
            scheduleDTO.setStatusCode(500);
        }

        return scheduleDTOs;
    }

    public List<ScheduleDTO> findSchedulesByPatientId(int patientId) {
        List<Schedule> schedules = scheduleRepo.findByPatientId(patientId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        try {
            for (Schedule schedule : schedules) {
                scheduleDTO.setMessage("List of patient id: " + patientId);
                scheduleDTO.setId(schedule.getId());
                scheduleDTO.setDoctorName(schedule.getDoctor().getDoctor().getName());
                scheduleDTO.setPatientName(schedule.getPatient().getUser().getName());
                scheduleDTO.setAppointmentDate(schedule.getAppointmentDate());

                scheduleDTO.setStatusCode(200);
                scheduleDTOs.add(scheduleDTO);
            }
        } catch (Exception e){
            scheduleDTO.setMessage("An Error: " + e);
            scheduleDTO.setStatusCode(500);
        }

        return scheduleDTOs;
    }
}
