package com.fx21314.asm3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "extrainfos")
@Data
public class ExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;

    @Column(name = "history_breath")
    private String historyBreath;

    @Column(name = "old_forms")
    private String oldForms;

    @Column(name = "send_forms")
    private String sendForms;

    @Column(name = "more_info")
    private String moreInfo;



    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @Column(name = "deletedAt")
    private Date deletedAt;



}