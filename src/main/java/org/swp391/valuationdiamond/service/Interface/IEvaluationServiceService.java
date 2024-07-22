package org.swp391.valuationdiamond.service.Interface;

import org.swp391.valuationdiamond.dto.EvaluationServiceDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;

import java.util.List;

public interface IEvaluationServiceService {
    EvaluationService createService(EvaluationServiceDTO evaluationServiceDTO);

    EvaluationService updateService(String serviceId, EvaluationServiceDTO evaluationServiceDTO);

    List<EvaluationService> getServices();

    EvaluationService getServiceById(String serviceId);

    boolean deleteServiceById(String serviceId);

    List<EvaluationService> getAllServices();
}
