package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.Clinic;
import com.fx21314.asm3.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepo extends JpaRepository<Clinic, Integer> {
    List<Clinic> findByRateGreaterThanEqual(int rateThreshold);

    Clinic findById(int id);
}
