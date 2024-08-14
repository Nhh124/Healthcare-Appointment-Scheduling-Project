package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.ExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtrainfoRepo extends JpaRepository<ExtraInfo, Integer> {

    Optional<ExtraInfo> findByPatientId(int patientId);

}
