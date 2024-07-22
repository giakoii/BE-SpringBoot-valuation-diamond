package org.swp391.valuationdiamond.service.Interface;

import org.swp391.valuationdiamond.dto.CommittedPaperDTO;
import org.swp391.valuationdiamond.entity.primary.CommittedPaper;

import java.util.List;

public interface ICommittedPaperService {
    CommittedPaper createCommittedPaper(CommittedPaperDTO committedPaperDTO);

    CommittedPaper getCommittedPaper(String id);

    List<CommittedPaper> getCommittedPaperByUserId(String userId);

    CommittedPaper getCommittedPaperByOrderId(String orderId);

    List<CommittedPaper> getAllCommittedPaper();
}
