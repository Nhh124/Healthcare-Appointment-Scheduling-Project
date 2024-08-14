package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findByRateGreaterThanEqual(int rateThreshold);

    Specialization findById(int id);
}