package com.fx21314.asm3.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "clinics")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phone;
    private String introductionHTML;
    private String introductionMarkDown;
    private String description;
    private String image;
    private Integer rate;
    private String workingHours;
    private String importantNotes;
    private BigDecimal consultationFee;
    private LocalDateTime createdAt;
}