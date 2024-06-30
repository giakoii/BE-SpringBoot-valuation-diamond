package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.EvaluationServicePriceList;

import java.util.List;

@Repository
public interface EvaluationServicePriceListReponsitory extends JpaRepository<EvaluationServicePriceList, String> {
    //    List<EvaluationServicePriceList> getEvaluationServicePriceListByServiceId(EvaluationService serviceId);
    List<EvaluationServicePriceList> findByServiceId(EvaluationService serviceId);

}
