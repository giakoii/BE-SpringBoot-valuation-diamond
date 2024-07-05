package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.EvaluationServicePriceListDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.EvaluationServicePriceList;
import org.swp391.valuationdiamond.repository.primary.EvaluationServicePriceListReponsitory;
import org.swp391.valuationdiamond.repository.primary.EvaluationServiceRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServicePriceListServiceImp implements IEvaluationServicePriceListService {
    private final EvaluationServicePriceListReponsitory evaluationServicePriceListRepository;
    private final EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    public EvaluationServicePriceListServiceImp(EvaluationServicePriceListReponsitory evaluationServicePriceListRepository, EvaluationServiceRepository evaluationServiceRepository) {
        this.evaluationServicePriceListRepository = evaluationServicePriceListRepository;
        this.evaluationServiceRepository = evaluationServiceRepository;
    }

    //============================================ CREATE ====================================================
    @Override
    public EvaluationServicePriceList createServicePriceList(EvaluationServicePriceListDTO evaluationServicePriceListDTO) {

            long count = evaluationServicePriceListRepository.count();
            String formattedCount = String.valueOf(count + 1);
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String servicePriceList = "ESPL" + date + formattedCount;

            if (evaluationServicePriceListDTO.getSizeFrom() >= evaluationServicePriceListDTO.getSizeTo()) {
                throw new IllegalArgumentException("Size from must be less than size to");
            }

            EvaluationService evaluationService = evaluationServiceRepository.findById(evaluationServicePriceListDTO.getServiceId()).orElseThrow(() -> new RuntimeException("Service not found"));
            try {
            EvaluationServicePriceList evaluationServicePriceList = EvaluationServicePriceList.builder()
                    .priceList(servicePriceList)
                    .initPrice(evaluationServicePriceListDTO.getInitPrice())
                    .priceUnit(evaluationServicePriceListDTO.getPriceUnit())
                    .sizeFrom(evaluationServicePriceListDTO.getSizeFrom())
                    .sizeTo(evaluationServicePriceListDTO.getSizeTo())
                    .serviceId(evaluationService)
                    .build();

            return evaluationServicePriceListRepository.save(evaluationServicePriceList);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the service price list", e);
        }
    }

    //============================================ GET ====================================================
    @Override
    public List<EvaluationServicePriceList> getPriceListByServiceId(String serviceId) {
        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        return evaluationServicePriceListRepository.findByServiceId(evaluationService);
    }

    @Override
    public List<EvaluationServicePriceList> getAllServicePriceList() {
        return evaluationServicePriceListRepository.findAll();
    }

    //======================================Calculate SampleSize===========================================
    double calculateServicePrice(double initPrice, float size, float sizeFrom) {
        double priceUnit = size < 12 ? 0 : 1200000;
        double unitPrice = initPrice + (size - sizeFrom) * priceUnit;
        return unitPrice;
    }

    @Override
    public double calculateServicePrice(String serviceId, float size) {
        if (size <= 2) {
            throw new IllegalArgumentException("Sample size must be greater than two");
        }

        EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Find the appropriate price list based on the sample size
        EvaluationServicePriceList evaluationServicePriceList = evaluationService.getServicePriceList().stream()
                .filter(pl -> size >= pl.getSizeFrom() && (pl.getSizeTo() == 0 || size < pl.getSizeTo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No price list available for the given sample size"));

        double initPrice = evaluationServicePriceList.getInitPrice();
        float sizeFrom = evaluationServicePriceList.getSizeFrom();

        return calculateServicePrice(initPrice, size, sizeFrom);
    }


    //================================= Hàm update ===================================
    //UPDATE PRICE LIST BY SERVICE ID
    @Override
    public List<EvaluationServicePriceList> updateServicePriceListByServiceId(String serviceId, EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
            List<EvaluationServicePriceList> evaluationServicePriceLists = evaluationServicePriceListRepository.findByServiceId(evaluationServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found")));

            EvaluationService evaluationService = evaluationServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
            EvaluationServicePriceList evaluationServicePriceList = evaluationServicePriceListRepository.findByServiceId(evaluationService).stream().findFirst().orElseThrow(() -> new RuntimeException("Price list not found with " + serviceId + " service id"));
        try {
            if (evaluationServicePriceListDTO.getSizeFrom() != null) {
                evaluationServicePriceList.setSizeFrom(evaluationServicePriceListDTO.getSizeFrom());
            }
            if (evaluationServicePriceListDTO.getSizeTo() != null) {
                evaluationServicePriceList.setSizeTo(evaluationServicePriceListDTO.getSizeTo());
            }
            if (evaluationServicePriceListDTO.getInitPrice() != null) {
                evaluationServicePriceList.setInitPrice(evaluationServicePriceListDTO.getInitPrice());
            }
            if (evaluationServicePriceListDTO.getPriceUnit() != null) {
                evaluationServicePriceList.setPriceUnit(evaluationServicePriceListDTO.getPriceUnit());
            }
            return evaluationServicePriceListRepository.saveAll(evaluationServicePriceLists);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the service price list", e);
        }

    }

    //UPDATE PRICE LIST BY ID
    @Override
    public EvaluationServicePriceList updateServicePriceListById(String id, EvaluationServicePriceListDTO evaluationServicePriceListDTO) {

        EvaluationServicePriceList evaluationServicePriceList = evaluationServicePriceListRepository.findById(id).orElseThrow(() -> new RuntimeException("Price list not found with " + id + " id"));
        if (evaluationServicePriceListDTO.getSizeFrom() >= evaluationServicePriceListDTO.getSizeTo()) {
            throw new IllegalArgumentException("Size from must be less than size to");
        }
        try {
            if (evaluationServicePriceListDTO.getSizeFrom() != null) {
                evaluationServicePriceList.setSizeFrom(evaluationServicePriceListDTO.getSizeFrom());
            }
            if (evaluationServicePriceListDTO.getSizeTo() != null) {
                evaluationServicePriceList.setSizeTo(evaluationServicePriceListDTO.getSizeTo());
            }
            if (evaluationServicePriceListDTO.getInitPrice() != null) {
                evaluationServicePriceList.setInitPrice(evaluationServicePriceListDTO.getInitPrice());
            }
            if (evaluationServicePriceListDTO.getPriceUnit() != null) {
                evaluationServicePriceList.setPriceUnit(evaluationServicePriceListDTO.getPriceUnit());
            }

            return evaluationServicePriceListRepository.save(evaluationServicePriceList);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the service price list", e);
        }
    }

    //============================================ DELETE ====================================================
    @Override
    public EvaluationServicePriceList deleteServicePriceListById(String id) {
        EvaluationServicePriceList evaluationServicePriceList = evaluationServicePriceListRepository.findById(id).orElseThrow(() -> new RuntimeException("Price list not found with " + id + " id"));
        evaluationServicePriceListRepository.delete(evaluationServicePriceList);
        return evaluationServicePriceList;
    }

}
