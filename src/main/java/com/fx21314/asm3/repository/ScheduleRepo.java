package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.DoctorUser;
import com.fx21314.asm3.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {
    List<Schedule> findByDoctorId(int id);

    List<Schedule> findByPatientId(int id);
}
