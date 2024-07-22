package org.swp391.valuationdiamond.service.Interface;

import org.swp391.valuationdiamond.dto.EvaluationResultDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationResult;

import java.util.List;

public interface IEvaluationResultService {
    EvaluationResult getResultByOrderDetailId(String orderDetailId);

    EvaluationResult createEvaluationResult(EvaluationResultDTO EvaluationResultDTO);

    EvaluationResult getEvaluationResult(String id);

    List<EvaluationResult> getResultByUserId(String userId);

    List<EvaluationResult> getAllEvaluationResult();

    EvaluationResult updateResult(String resultId, EvaluationResultDTO evaluationResultDTO);


}
