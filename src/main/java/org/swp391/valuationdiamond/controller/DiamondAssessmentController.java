package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.DiamondAssessmentDTO;
import org.swp391.valuationdiamond.entity.primary.DiamondAssessment;
import org.swp391.valuationdiamond.service.DiamondAssessmentServiceImp;

import java.util.List;

@RestController
@RequestMapping("/diamond_assessment")
public class DiamondAssessmentController {

    @Autowired
    private DiamondAssessmentServiceImp diamondAssessmentServiceImp;

    @PostMapping("/create")
    DiamondAssessment createDiamondAssessment(@RequestBody DiamondAssessmentDTO diamondAssessmentDTO) {
        return diamondAssessmentServiceImp.createDiamondAssessment(diamondAssessmentDTO);

    }
    @GetMapping("/getDiamondAssessments")
    List<DiamondAssessment> getDiamondAssessments(){
        {
            return diamondAssessmentServiceImp.getDiamondAssessmentList();
        }
    }
    @GetMapping("/getDiamondAssessmentById/{diamondAssessmentId}")
    DiamondAssessment getDiamondAssessments(@PathVariable("diamondAssessmentId") String diamondAssessmentId) {
        return diamondAssessmentServiceImp.getDiamondAssessment(diamondAssessmentId);
    }


}
