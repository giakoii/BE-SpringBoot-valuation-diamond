package org.swp391.valuationdiamond.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.EvaluationRequestDTO;

import jakarta.transaction.Transactional;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.repository.primary.EvaluationRequestRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EvaluationRequestServiceImp implements IEvaluationRequestService {
    @Autowired
    private EvaluationRequestRepository evaluationRequestRepository;

    @Autowired
    private UserRepository userRepository;


    //Hàm create request 'C'
    @Override
    @Transactional
    public EvaluationRequest createEvaluationRequest(EvaluationRequestDTO evaluationRequestDTO) {
        EvaluationRequest evaluationRequest = new EvaluationRequest();

        long count = evaluationRequestRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestId = "ER"+ date + formattedCount ;

        evaluationRequest.setRequestId(requestId);
        evaluationRequest.setRequestDescription(evaluationRequestDTO.getRequestDescription());
        evaluationRequest.setRequestDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        evaluationRequest.setRequestEmail(evaluationRequestDTO.getRequestEmail());
        evaluationRequest.setGuestName(evaluationRequestDTO.getGuestName());
        evaluationRequest.setStatus("Requesting");
        evaluationRequest.setService(evaluationRequestDTO.getService());
        evaluationRequest.setPhoneNumber(evaluationRequestDTO.getPhoneNumber());
        evaluationRequest.setMeetingDate(evaluationRequestDTO.getMeetingDate());


        User userId = userRepository.findById(evaluationRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        evaluationRequest.setUserId(userId);

        return evaluationRequestRepository.save(evaluationRequest);
    }

    //Hàm read 1 evaluation request 'R'
    @Override
    public EvaluationRequest getEvaluationRequest(String requestId) {
        if (evaluationRequestRepository.findByRequestId(requestId) == null) {
            throw new RuntimeException("Evaluation Request not found");
        }

        return evaluationRequestRepository.findByRequestId(requestId);
    }

    //show evaluation request 'R'
    @Override
    public List<EvaluationRequest> getAllEvaluationRequest() {
        return evaluationRequestRepository.findAll();
    }

    //show by status 'R'
    @Override
    public List<EvaluationRequest> getEvaluationRequestByStatus(String status) {
        return evaluationRequestRepository.findByStatus(status);
    }

    //hàm delete 'D'
    @Override
    public boolean deleteEvaluationRequest(String requestId) {
        EvaluationRequest evaluationRequest = evaluationRequestRepository.findByRequestId(requestId);
        if (evaluationRequestRepository.findByRequestId(requestId) == null) {
            throw new RuntimeException("Evaluation Request not found");
        }
        evaluationRequestRepository.delete(evaluationRequest);
        return true;
    }

    //hàm update 'U'
    @Override
    public EvaluationRequest updateEvaluationRequest(String requestId, EvaluationRequestDTO evaluationRequestDTO) {
        EvaluationRequest evaluationRequest = evaluationRequestRepository.findByRequestId(requestId);
        if (evaluationRequestRepository.findByRequestId(requestId) == null) {
            throw new RuntimeException("Evaluation Request not found");
        }
        if (evaluationRequestDTO.getRequestDescription() != null) {
            evaluationRequest.setRequestDescription(evaluationRequestDTO.getRequestDescription());
        }
        if (evaluationRequestDTO.getRequestEmail() != null) {
            evaluationRequest.setRequestEmail(evaluationRequestDTO.getRequestEmail());
        }
        if (evaluationRequestDTO.getGuestName() != null) {
            evaluationRequest.setGuestName(evaluationRequestDTO.getGuestName());
        }
        if (evaluationRequestDTO.getPhoneNumber() != null) {
            evaluationRequest.setPhoneNumber(evaluationRequestDTO.getPhoneNumber());
        }
        if (evaluationRequestDTO.getService() != null) {
            evaluationRequest.setService(evaluationRequestDTO.getService());
        }
        if (evaluationRequestDTO.getStatus() != null) {
            evaluationRequest.setStatus(evaluationRequestDTO.getStatus());
        }
        if (evaluationRequestDTO.getMeetingDate() != null) {
            evaluationRequest.setMeetingDate(evaluationRequestDTO.getMeetingDate());
        }
        return evaluationRequestRepository.save(evaluationRequest);
    }


    @Override
    public List<EvaluationRequest> getRequestByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return evaluationRequestRepository.findByUserId(user);
    }
}
