package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.DoctorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorUserRepo extends JpaRepository<DoctorUser, Integer> {

    DoctorUser findByDoctorId(int doctorId);

    @Query("SELECT du FROM DoctorUser du JOIN du.doctor u WHERE u.name LIKE %:name%")
    DoctorUser findByNameContaining(@Param("name") String name);

    @Query("SELECT du FROM DoctorUser du JOIN du.specialization s WHERE LOWER(s.name) LIKE %:keyword%")
    List<DoctorUser> findBySpecializationNameContainingIgnoreCase(@Param("keyword") String keyword);
}