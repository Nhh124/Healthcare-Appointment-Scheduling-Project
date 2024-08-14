package com.fx21314.asm3.repository;

import com.fx21314.asm3.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepo extends JpaRepository<Status,Integer> {

    Optional<Status> findById(int id);
}
