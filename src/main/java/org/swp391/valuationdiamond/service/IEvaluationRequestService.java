package org.swp391.valuationdiamond.service;

import java.util.List;

import org.swp391.valuationdiamond.dto.EvaluationRequestDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.Status;

public interface IEvaluationRequestService {
  EvaluationRequest createEvaluationRequest(EvaluationRequestDTO evaluationRequest);
  EvaluationRequest getEvaluationRequest(String requestId);
  List<EvaluationRequest> getAllEvaluationRequest();
  List<EvaluationRequest> getEvaluationRequestByStatus(Status status);

  List<EvaluationRequest> getRequestByUser(String userId);

  boolean deleteEvaluationRequest(String requestId);
  EvaluationRequest updateEvaluationRequest(String requestId, EvaluationRequestDTO evaluationRequestDTO);


}
