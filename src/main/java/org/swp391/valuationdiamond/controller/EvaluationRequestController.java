package org.swp391.valuationdiamond.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.EvaluationRequestDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.Status;
import org.swp391.valuationdiamond.service.Implement.EvaluationRequestServiceImp;
import org.swp391.valuationdiamond.service.Interface.IEvaluationRequestService;

@RestController
@RequestMapping("/evaluation-request")
public class EvaluationRequestController {

    @Autowired
    private IEvaluationRequestService evaluationRequestService;
    @Autowired
    private EvaluationRequestServiceImp evaluationRequestServiceImp;


    //=============================== API CREATE ======================================

    @PostMapping("/create")
    public EvaluationRequest createEvaluationRequest(@RequestBody EvaluationRequestDTO evaluationRequest) {
        return evaluationRequestService.createEvaluationRequest(evaluationRequest);
    }


    //=============================== API GET ======================================


    @GetMapping("/{requestId}")
    public EvaluationRequest getEvaluationRequest(@PathVariable("requestId") String requestId) {
        return evaluationRequestService.getEvaluationRequest(requestId);
    }

    @GetMapping("/list/{status}")
    public List<EvaluationRequest> getEvaluationRequestByStatus(@PathVariable("status") Status status) {
        return evaluationRequestService.getEvaluationRequestByStatus(status);
    }

    @GetMapping("/gett_all")
    public List<EvaluationRequest> getAllEvaluationRequest() {
        return evaluationRequestService.getAllEvaluationRequest();
    }

    //API get request by user
    @GetMapping("/get_by_user/{userId}")
    public List<EvaluationRequest> getRequestByUser(@PathVariable("userId") String userId) {
        return evaluationRequestService.getRequestByUser(userId);
    }


    //=============================== API UPDATE ======================================

    @PutMapping("/update/{requestId}")
    public EvaluationRequest updateRequest(@PathVariable("requestId") String requestId, @RequestBody EvaluationRequestDTO evaluationRequestDTO) {
        return evaluationRequestServiceImp.updateEvaluationRequest(requestId, evaluationRequestDTO);
    }

    //=============================== API DELETE ======================================

    @DeleteMapping("/delete/{requestId}")
    public boolean deleteRequest(@PathVariable("requestId") String requestId) {
        return evaluationRequestService.deleteEvaluationRequest(requestId);
    }

}

