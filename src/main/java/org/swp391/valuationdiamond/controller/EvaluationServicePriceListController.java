package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.EvaluationServicePriceListDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationServicePriceList;
import org.swp391.valuationdiamond.service.EvaluationServicePriceListServiceImp;
import org.swp391.valuationdiamond.service.IEvaluationServicePriceListService;

import java.util.List;

@RestController
@RequestMapping("/service_price_list")
public class EvaluationServicePriceListController {
    @Autowired
    IEvaluationServicePriceListService evaluationServicePriceListServiceImp;

    //============================================ CREATE ====================================================
    @PostMapping("/create")
    public EvaluationServicePriceList createServicePriceList(@RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        return evaluationServicePriceListServiceImp.createServicePriceList(evaluationServicePriceListDTO);
    }

    //============================================ GET ====================================================
    @GetMapping("/getServicePriceListByServiceId/{serviceId}")
    public List<EvaluationServicePriceList> getPriceListByServiceId(@PathVariable("serviceId") String serviceId) {
        return evaluationServicePriceListServiceImp.getPriceListByServiceId(serviceId);
    }

    @GetMapping("/getServicePriceLists")
    public List<EvaluationServicePriceList> getAllEvaluationPriceList() {
        return evaluationServicePriceListServiceImp.getAllServicePriceList();
    }

    //    @GetMapping("/calculate")
//    public double calculatePrice(@RequestParam String serviceId, @RequestParam float sampleSize) {
//        return evaluationServicePriceListServiceImp.calculateServicePrice(serviceId, sampleSize);
//    }

    //======================================Calculate SampleSize===========================================
    @GetMapping("/calculate")
    public ResponseEntity<?> calculatePrice(
            @RequestParam String serviceId,
            @RequestParam float size) {

        try {
            double price = evaluationServicePriceListServiceImp.calculateServicePrice(serviceId, size);
            return ResponseEntity.ok(price);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PutMapping("/updateServicePriceList/{serviceId}")
    public List<EvaluationServicePriceList> updateByServiceId(@PathVariable String serviceId, @RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        return evaluationServicePriceListServiceImp.updateServicePriceListByServiceId(serviceId, evaluationServicePriceListDTO);
    }

    @PutMapping("/updateServicePriceListById/{id}")
    public EvaluationServicePriceList updateById(@PathVariable String id, @RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        return evaluationServicePriceListServiceImp.updateServicePriceListById(id, evaluationServicePriceListDTO);
    }


    //============================================ DELETE ====================================================
    @DeleteMapping("/deleteServicePriceListById/{id}")
    public EvaluationServicePriceList deleteServicePriceListById(@PathVariable("id") String id) {
        return evaluationServicePriceListServiceImp.deleteServicePriceListById(id);
    }


}
