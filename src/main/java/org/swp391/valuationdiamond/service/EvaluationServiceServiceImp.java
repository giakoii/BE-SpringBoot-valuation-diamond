package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.EvaluationServiceDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.EvaluationServicePriceList;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.repository.primary.EvaluationServicePriceListReponsitory;
import org.swp391.valuationdiamond.repository.primary.EvaluationServiceRepository;
import org.swp391.valuationdiamond.repository.primary.OrderDetailRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class EvaluationServiceServiceImp {
    @Autowired
    EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    EvaluationServicePriceListReponsitory evaluationServicePriceListReponsitory;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    //============================================ Hàm create ========================================
    public EvaluationService createService(EvaluationServiceDTO evaluationServiceDTO) {
        try {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        long count = evaluationServiceRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String serviceId = "SV" + date + formattedCount;

        EvaluationService evaluationService = EvaluationService.builder()
                .serviceId(serviceId)
                .serviceType(evaluationServiceDTO.getServiceType())
                .serviceDescription(evaluationServiceDTO.getServiceDescription())
                .build();
        return evaluationServiceRepository.save(evaluationService);
        } catch (Exception e) {
        throw new RuntimeException("An error occurred while creating the evaluation service", e);
    }
    }
    //============================================ Hàm update ========================================
    public EvaluationService updateService(String serviceId,EvaluationServiceDTO evaluationServiceDTO) {
       try {
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        if(evaluationServiceDTO.getServiceType() != null){
            evaluationService.setServiceType(evaluationServiceDTO.getServiceType());
        }
        if(evaluationServiceDTO.getServiceDescription() != null){
            evaluationService.setServiceDescription(evaluationServiceDTO.getServiceDescription());
        }
        return evaluationServiceRepository.save(evaluationService);
       } catch (Exception e) {
           throw new RuntimeException("An error occurred while updating the evaluation service", e);
       }
    }
    //=============================================== Các hàm Get ========================================
    public List<EvaluationService> getServices() {
        return  evaluationServiceRepository.findAll();
    }

    public EvaluationService getServiceById(String serviceId) {
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return evaluationService;
    }

    //============================================ Hàm delete ========================================
    public boolean deleteServiceById(String serviceId){
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        evaluationServiceRepository.delete(evaluationService);
        return true;
    }


}
