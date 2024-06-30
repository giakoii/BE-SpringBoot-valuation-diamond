package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.EvaluationResultDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationResult;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.repository.primary.EvaluationResultRepository;
import org.swp391.valuationdiamond.repository.primary.OrderDetailRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EvaluationResultServiceImp {
    @Autowired
    private EvaluationResultRepository evaluationResultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    //============================================ CREATE =====================================
    public EvaluationResult createEvaluationResult(EvaluationResultDTO EvaluationResultDTO){
        long count = evaluationResultRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String ResultId = "ERS"+ date + formattedCount ;


        EvaluationResult evaluationResult = EvaluationResult.builder()
                .evaluationResultId(ResultId)
                .diamondOrigin(EvaluationResultDTO.getDiamondOrigin())
                .measurements(EvaluationResultDTO.getMeasurements())
                .proportions(EvaluationResultDTO.getProportions())
                .shapeCut(EvaluationResultDTO.getShapeCut())
                .caratWeight(EvaluationResultDTO.getCaratWeight())
                .color(EvaluationResultDTO.getColor())
                .clarity(EvaluationResultDTO.getClarity())
                .cut(EvaluationResultDTO.getCut())
                .symmetry(EvaluationResultDTO.getSymmetry())
                .polish(EvaluationResultDTO.getPolish())
                .fluorescence(EvaluationResultDTO.getFluorescence())
                .description(EvaluationResultDTO.getDescription())
                .price(EvaluationResultDTO.getPrice())
                .img(EvaluationResultDTO.getImg())
                .build();

        User userId = userRepository.findById(EvaluationResultDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        evaluationResult.setUserId(userId);

        OrderDetail orderDetail = orderDetailRepository.findById(EvaluationResultDTO.getOrderDetailId()).orElseThrow(() -> new RuntimeException("Order detail not found"));
        evaluationResult.setOrderDetailId(orderDetail);



        return evaluationResultRepository.save(evaluationResult);

    }

    //============================================ GET =====================================
    public EvaluationResult getEvaluationResult(String id){
        return evaluationResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public List<EvaluationResult> getResultByOrderDetailId(String orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new RuntimeException("Order Detail not found"));

        return evaluationResultRepository.findByOrderDetailId(orderDetail);
    }

    public List<EvaluationResult> getResultByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return evaluationResultRepository.findByUserId(user);
    }

    public List<EvaluationResult> getAllEvaluationResult() {
        return evaluationResultRepository.findAll();
    }

    public EvaluationResult updateResult(String resultId, EvaluationResultDTO evaluationResultDTO){
        EvaluationResult result= evaluationResultRepository.findById(resultId).orElseThrow(() -> new RuntimeException("Evaluation Result not found"));

        if (evaluationResultDTO.getDiamondOrigin() != null) {
            result.setDiamondOrigin(evaluationResultDTO.getDiamondOrigin());
        }
        if (evaluationResultDTO.getMeasurements() != null) {
            result.setMeasurements(evaluationResultDTO.getMeasurements());
        }
        if (evaluationResultDTO.getProportions() != null) {
            result.setProportions(evaluationResultDTO.getProportions());
        }
        if (evaluationResultDTO.getShapeCut() != null) {
            result.setShapeCut(evaluationResultDTO.getShapeCut());
        }
        if (evaluationResultDTO.getCaratWeight() != null) {
            result.setCaratWeight(evaluationResultDTO.getCaratWeight());
        }
        if (evaluationResultDTO.getColor() != null) {
            result.setColor(evaluationResultDTO.getColor());
        }
        if (evaluationResultDTO.getClarity() != null) {
            result.setClarity(evaluationResultDTO.getClarity());
        }
        if (evaluationResultDTO.getCut() != null) {
            result.setCut(evaluationResultDTO.getCut());
        }
        if (evaluationResultDTO.getSymmetry() != null) {
            result.setSymmetry(evaluationResultDTO.getSymmetry());
        }
        if (evaluationResultDTO.getPolish() != null) {
            result.setPolish(evaluationResultDTO.getPolish());
        }
        if (evaluationResultDTO.getFluorescence() != null) {
            result.setFluorescence(evaluationResultDTO.getFluorescence());
        }
        if (evaluationResultDTO.getDescription() != null) {
            result.setDescription(evaluationResultDTO.getDescription());
        }
        if (evaluationResultDTO.getPrice() != null) {
            result.setPrice(evaluationResultDTO.getPrice());
        }
        if (evaluationResultDTO.getImg() != null) {
            result.setImg(evaluationResultDTO.getImg());
        }


        return evaluationResultRepository.save(result);
    }
}





