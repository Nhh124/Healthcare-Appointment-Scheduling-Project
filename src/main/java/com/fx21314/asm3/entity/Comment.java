//package com.fx21314.asm3.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "comments")
//public class Comment {
//
//    @Id
//    private int id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "contentMarkdown")
//    private String contentMarkdown;
//
//    @Column(name = "contentHTML")
//    private String contentHTML;
//
//    @Column(name = "forDoctorId")
//    private int forDoctorId;
//
//    @Column(name = "forSpecializationId")
//    private int forSpecializationId;
//
//    @Column(name = "forClinicId")
//    private int forClinicId;
//
//    @Column(name = "writerId")
//    private int writerId;
//
//    @Column(name = "confirmByDoctor")
//    private boolean confirmByDoctor;
//
//    @Column(name = "image")
//    private String image;
//
//    @Column(name = "createdAt")
//    private Date createdAt;
//
//    @Column(name = "updatedAt")
//    private Date updatedAt;
//
//    @Column(name = "deletedAt")
//    private Date deletedAt;
//}