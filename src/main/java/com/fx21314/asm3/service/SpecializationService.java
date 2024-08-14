package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.SpecializationDTO;
import com.fx21314.asm3.entity.Specialization;
import com.fx21314.asm3.repository.SpecializationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    public SpecializationDTO createSpecialization(SpecializationDTO specializationDTO) {
        SpecializationDTO resp = new SpecializationDTO();
        try {
            Specialization specialization = new Specialization();
            specialization.setName(specializationDTO.getName());
            specialization.setDescription(specializationDTO.getDescription());
            specialization.setImage(specializationDTO.getImage());
            specialization.setCreatedAt(LocalDateTime.now());

            Specialization savedSpecialization = specializationRepository.save(specialization);
            if (savedSpecialization != null && savedSpecialization.getId() > 0){
                resp.setMessage("Spec Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch ( Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public List<SpecializationDTO> findSpecializationsByRate() {
        List<SpecializationDTO> specializationDTOList = new ArrayList<>();
        SpecializationDTO resp = new SpecializationDTO(); // Để điều kiện trả lỗi

        try {
            List<Specialization> specializations = specializationRepository.findByRateGreaterThanEqual(50);

            if (specializations.isEmpty()) {
                resp.setMessage("No specializations found with rate greater than or equal to 50");
                resp.setStatusCode(404);
                specializationDTOList.add(resp);
            } else {
                specializationDTOList = specializations.stream()
                        .map(specialization -> {
                            SpecializationDTO dto = new SpecializationDTO();
                            dto.setName(specialization.getName());
                            dto.setStatusCode(200);
                            dto.setMessage("List specialization:");
                            dto.setDescription(specialization.getDescription());
                            return dto;
                        })
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            resp.setMessage("An error occurred: " + e.getMessage());
            resp.setStatusCode(500);
            specializationDTOList.add(resp);
        }

        return specializationDTOList;
    }

//    public SpecializationDTO updateSpecialization(int id, SpecializationDTO specializationDTO) {
//        Specialization specialization = specializationRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Specialization not found with id: " + id));
//
//        specialization.setName(specializationDTO.getName());
//        specialization.setDescription(specializationDTO.getDescription());
//        specialization.setImage(specializationDTO.getImage());
//        specialization.setUpdatedAt(LocalDateTime.now());
//
//        Specialization updatedSpecialization = specializationRepository.save(specialization);
//        return mapToDTO(updatedSpecialization);
//    }

//    public void deleteSpecialization(int id) {
//        Specialization specialization = specializationRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Specialization not found with id: " + id));
//
//        specialization.setDeletedAt(LocalDateTime.now());
//        specializationRepository.save(specialization);
//    }


}
