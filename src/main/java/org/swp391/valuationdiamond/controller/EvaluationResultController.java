package org.swp391.valuationdiamond.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.swp391.valuationdiamond.dto.EvaluationResultDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationResult;
import org.swp391.valuationdiamond.service.Implement.EvaluationResultServiceImp;

import java.util.List;

@RestController
@RequestMapping("/evaluation_results")
public class EvaluationResultController {

    @Autowired
    private EvaluationResultServiceImp evaluationResultServiceImp;


    //===================================== CREATE EVALUATION RESULT =====================================
    @PostMapping("/create")
    EvaluationResult createEvaluationResult(@Valid @RequestBody EvaluationResultDTO evaluationResultDTO) {
        return evaluationResultServiceImp.createEvaluationResult(evaluationResultDTO);

    }

    //===================================== GET EVALUATION RESULT =====================================
    @GetMapping("/getEvaluationResults")
    List<EvaluationResult> getEvaluationResults() {
        return evaluationResultServiceImp.getAllEvaluationResult();
    }

    @GetMapping("/getEvaluationResults/{evaluationResultId}")
    EvaluationResult getEvaluationResult(@PathVariable String evaluationResultId) {
        return evaluationResultServiceImp.getEvaluationResult(evaluationResultId);
    }

    @GetMapping("/getEvaluationResultsByOrderDetailId/{orderDetailId}")
    public EvaluationResult getEvaluationResultByOrderDetailId(@PathVariable("orderDetailId") String orderDetailId) {
        try {
            return evaluationResultServiceImp.getResultByOrderDetailId(orderDetailId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order detail not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the evaluation result");
        }
    }

    @GetMapping("/getEvaluationResultsByUserId/{userId}")
    public List<EvaluationResult> getEvaluationResultsByUserId(@PathVariable("userId") String userId) {
        try {
            return evaluationResultServiceImp.getResultByUserId(userId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the evaluation results");
        }

    }

    //===================================== UPDATE EVALUATION RESULT =====================================

    @PutMapping("/updateEvaluationResult/{resultId}")
    public EvaluationResult updateResult(@PathVariable("resultId") String resultId, @RequestBody EvaluationResultDTO evaluationResultDTO) {
        return evaluationResultServiceImp.updateResult(resultId, evaluationResultDTO);
    }
}

