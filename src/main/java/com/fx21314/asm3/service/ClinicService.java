package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.ClinicDTO;
import com.fx21314.asm3.dto.ClinicSpecializationDTO;
import com.fx21314.asm3.entity.Clinic;
import com.fx21314.asm3.entity.ClinicSpecialization;
import com.fx21314.asm3.repository.ClinicRepo;
import com.fx21314.asm3.repository.ClinicSpecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepo clinicRepository;

    @Autowired
    private ClinicSpecRepo clinicSpecRepo;


    public List<ClinicSpecializationDTO> findByKeyword(String keyword) {
        List<ClinicSpecializationDTO> specializationDTOList = new ArrayList<>();
        try {
            List<ClinicSpecialization> specializations = clinicSpecRepo.findByKeyword(keyword);
            if (specializations.isEmpty()) {
                ClinicSpecializationDTO resp = new ClinicSpecializationDTO();
                resp.setMessage("No clinic specializations found for keyword: " + keyword);
                resp.setStatusCode(404);
                specializationDTOList.add(resp);
            } else {
                ClinicSpecializationDTO resp = new ClinicSpecializationDTO();
                resp.setMessage("List of clinic specializations for keyword " + keyword + ":");
                resp.setStatusCode(200);
                specializationDTOList.add(resp);

                specializationDTOList.addAll(specializations.stream()
                        .map(specialization -> {
                            ClinicSpecializationDTO dto = new ClinicSpecializationDTO();
                            dto.setClinic(specialization.getClinic());
                            dto.setSpecializations(specialization.getSpecialization());
                            dto.setStatusCode(200);
                            return dto;
                        })
                        .collect(Collectors.toList()));
            }
        } catch (Exception e) {
            ClinicSpecializationDTO resp = new ClinicSpecializationDTO();
            resp.setMessage("An error occurred: " + e.getMessage());
            resp.setStatusCode(500);
            specializationDTOList.add(resp);
        }
        return specializationDTOList;
    }

    public ClinicDTO createClinic(ClinicDTO clinicDTO) {
        ClinicDTO resp = new ClinicDTO();
        try {
            Clinic clinic = new Clinic();
            clinic.setName(clinicDTO.getName());
            clinic.setAddress(clinicDTO.getAddress());
            clinic.setPhone(clinicDTO.getPhone());
            clinic.setIntroductionHTML(clinicDTO.getIntroductionHTML());
            clinic.setIntroductionMarkDown(clinicDTO.getIntroductionMarkDown());
            clinic.setDescription(clinicDTO.getDescription());
            clinic.setImage(clinicDTO.getImage());
            clinic.setRate(clinicDTO.getRate());
            clinic.setWorkingHours(clinicDTO.getWorkingHours());
            clinic.setImportantNotes(clinicDTO.getImportantNotes());
            clinic.setConsultationFee(clinicDTO.getConsultationFee());
            clinic.setCreatedAt(LocalDateTime.now());

            Clinic savedClinic = clinicRepository.save(clinic);
            if (savedClinic != null && savedClinic.getId() > 0) {
                resp.setMessage("Clinic Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public List<ClinicDTO> findClinicsByRate() {
        List<ClinicDTO> clinicDTOList = new ArrayList<>();

        try {
            List<Clinic> clinics = clinicRepository.findByRateGreaterThanEqual(50);

            if (clinics.isEmpty()) {
                ClinicDTO resp = new ClinicDTO();
                resp.setMessage("No clinics found with rate greater than or equal to 50");
                resp.setStatusCode(404);
                clinicDTOList.add(resp);
            } else {
                ClinicDTO resp = new ClinicDTO();
                resp.setMessage("List of clinics:");
                resp.setStatusCode(200);
                clinicDTOList.add(resp);

                clinicDTOList.addAll(clinics.stream()
                        .map(clinic -> {
                            ClinicDTO dto = new ClinicDTO();
                            dto.setName(clinic.getName());
                            dto.setAddress(clinic.getAddress());
                            dto.setRate(clinic.getRate());
                            dto.setStatusCode(200);
                            return dto;
                        })
                        .collect(Collectors.toList()));
            }
        } catch (Exception e) {
            ClinicDTO resp = new ClinicDTO();
            resp.setMessage("An error occurred: " + e.getMessage());
            resp.setStatusCode(500);
            clinicDTOList.add(resp);
        }

        return clinicDTOList;
    }


}
