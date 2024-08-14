package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.DoctorUser;
import com.fx21314.asm3.entity.Patient;
import com.fx21314.asm3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {

    Optional<Patient> findByUserId(int id);

    @Query("SELECT p FROM Patient p JOIN User u ON p.user.id = u.id WHERE u.name LIKE %:name%")
    Optional<Patient> findByNameContaining(@Param("name") String name);

    List<Patient> findByDoctorId(int id);
}
