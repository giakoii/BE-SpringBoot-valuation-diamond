package org.swp391.valuationdiamond.service;

import org.swp391.valuationdiamond.dto.DiamondAssessmentDTO;
import org.swp391.valuationdiamond.entity.primary.DiamondAssessment;

import java.util.List;

public interface IDiamondAssessmentService {
    DiamondAssessment createDiamondAssessment(DiamondAssessmentDTO diamondAssessmentDTO);
    DiamondAssessment getDiamondAssessment(String id);
    List<DiamondAssessment> getDiamondAssessmentList();
}
