package org.swp391.valuationdiamond.service.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.EvaluationServiceDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.Status;
import org.swp391.valuationdiamond.repository.primary.EvaluationServicePriceListReponsitory;
import org.swp391.valuationdiamond.repository.primary.EvaluationServiceRepository;
import org.swp391.valuationdiamond.repository.primary.OrderDetailRepository;
import org.swp391.valuationdiamond.service.Interface.IEvaluationServiceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EvaluationServiceServiceImp implements IEvaluationServiceService {
    @Autowired
    EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    EvaluationServicePriceListReponsitory evaluationServicePriceListReponsitory;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    //============================================ Hàm create ========================================
    @Override
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
                    .status(Status.ENABLE)
                    .build();
            return evaluationServiceRepository.save(evaluationService);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the evaluation service", e);
        }
    }

    //============================================ Hàm update ========================================
    @Override
    public EvaluationService updateService(String serviceId, EvaluationServiceDTO evaluationServiceDTO) {

        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        try {
            if (evaluationServiceDTO.getServiceType() != null) {
                evaluationService.setServiceType(evaluationServiceDTO.getServiceType());
            }
            if (evaluationServiceDTO.getServiceDescription() != null) {
                evaluationService.setServiceDescription(evaluationServiceDTO.getServiceDescription());
            }
            if (evaluationServiceDTO.getStatus() != null) {
                evaluationService.setStatus(Status.valueOf(evaluationServiceDTO.getStatus()));
            }
            return evaluationServiceRepository.save(evaluationService);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the evaluation service", e);
        }
    }

    //=============================================== Các hàm Get ========================================
    @Override
    public List<EvaluationService> getServices() {
        return evaluationServiceRepository.findByStatus(Status.ENABLE);
    }

    @Override
    public EvaluationService getServiceById(String serviceId) {
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        if (evaluationService.getStatus() == Status.ENABLE) {
            return evaluationService;
        }
        throw new RuntimeException("Service is disabled");
    }

    @Override
    public List<EvaluationService> getAllServices() {
        return evaluationServiceRepository.findAll();
    }

    //============================================ Hàm delete ========================================
    @Override
    public boolean deleteServiceById(String serviceId) {
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        evaluationServiceRepository.delete(evaluationService);
        return true;
    }


}
