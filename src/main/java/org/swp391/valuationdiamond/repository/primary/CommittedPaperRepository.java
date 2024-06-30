package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.CommittedPaper;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

@Repository
public interface CommittedPaperRepository extends JpaRepository<CommittedPaper, String> {
    List<CommittedPaper> findByUserId(User userId);
    List<CommittedPaper> findByOrderId(Order orderId);
}
