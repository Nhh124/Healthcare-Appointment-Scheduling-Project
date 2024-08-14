package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.ClinicSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicSpecRepo extends JpaRepository<ClinicSpecialization, Integer> {
    @Query("SELECT cs FROM ClinicSpecialization cs JOIN cs.clinic c JOIN cs.specialization s WHERE c.address LIKE %:keyword% OR s.name LIKE %:keyword% OR c.name LIKE %:keyword% OR CAST(c.consultationFee as string) LIKE %:keyword%")
    List<ClinicSpecialization> findByKeyword(@Param("keyword") String keyword);
}
