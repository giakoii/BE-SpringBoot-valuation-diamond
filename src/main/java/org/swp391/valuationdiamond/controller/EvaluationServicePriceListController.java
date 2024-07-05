package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.EvaluationServicePriceListDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationServicePriceList;
import org.swp391.valuationdiamond.service.IEvaluationServicePriceListService;

import java.util.List;

@RestController
@RequestMapping("/service_price_list")
public class EvaluationServicePriceListController {
    @Autowired
    IEvaluationServicePriceListService evaluationServicePriceListServiceImp;

    //============================================ CREATE ====================================================
    @PostMapping("/create")
    public ResponseEntity<?> createServicePriceList(@RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        try {
            EvaluationServicePriceList createdServicePriceList = evaluationServicePriceListServiceImp.createServicePriceList(evaluationServicePriceListDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdServicePriceList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    //============================================ GET ====================================================
    @GetMapping("/getServicePriceListByServiceId/{serviceId}")
    public ResponseEntity<?> getPriceListByServiceId(@PathVariable("serviceId") String serviceId) {
        try {
            List<EvaluationServicePriceList> servicePriceList = evaluationServicePriceListServiceImp.getPriceListByServiceId(serviceId);
            return ResponseEntity.ok(servicePriceList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/getServicePriceLists")
    public ResponseEntity<?> getAllEvaluationPriceList() {
        try {
            List<EvaluationServicePriceList> servicePriceLists = evaluationServicePriceListServiceImp.getAllServicePriceList();
            return ResponseEntity.ok(servicePriceLists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    //====================================== Calculate SampleSize ===========================================
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
    public ResponseEntity<?> updateByServiceId(@PathVariable String serviceId, @RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        try {
            List<EvaluationServicePriceList> updatedServicePriceList = evaluationServicePriceListServiceImp.updateServicePriceListByServiceId(serviceId, evaluationServicePriceListDTO);
            return ResponseEntity.ok(updatedServicePriceList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PutMapping("/updateServicePriceListById/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody EvaluationServicePriceListDTO evaluationServicePriceListDTO) {
        try {
            EvaluationServicePriceList updatedServicePriceList = evaluationServicePriceListServiceImp.updateServicePriceListById(id, evaluationServicePriceListDTO);
            return ResponseEntity.ok(updatedServicePriceList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    //============================================ DELETE ====================================================
    @DeleteMapping("/deleteServicePriceListById/{id}")
    public ResponseEntity<?> deleteServicePriceListById(@PathVariable("id") String id) {
        try {
            EvaluationServicePriceList deletedServicePriceList = evaluationServicePriceListServiceImp.deleteServicePriceListById(id);
            return ResponseEntity.ok(deletedServicePriceList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
