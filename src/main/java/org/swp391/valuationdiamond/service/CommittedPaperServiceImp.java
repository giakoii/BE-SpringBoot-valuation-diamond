package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.CommittedPaperDTO;
import org.swp391.valuationdiamond.entity.primary.CommittedPaper;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.repository.primary.CommittedPaperRepository;
import org.swp391.valuationdiamond.repository.primary.OrderRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommittedPaperServiceImp {
    @Autowired
    private CommittedPaperRepository committedPaperRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    //check lại logic hàm này
    //user phải là người tạo ra cái order đó, không cho nhập bừa
    public CommittedPaper createCommittedPaper(CommittedPaperDTO  committedPaperDTO) {
       try {
        CommittedPaper committedPaper = new CommittedPaper();
        long count = committedPaperRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String CommitId = "CO" + formattedCount + date;

        committedPaper.setCommittedId(CommitId);
        committedPaper.setCommittedName(committedPaperDTO.getCommittedName());
        committedPaper.setCommittedDate(committedPaperDTO.getCommittedDate());
        committedPaper.setCivilId(committedPaperDTO.getCivilId());

        User userId = userRepository.findById(committedPaperDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        committedPaper.setUserId(userId);
        Order orderId = orderRepository.findById(committedPaperDTO.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        committedPaper.setOrderId(orderId);

        return committedPaperRepository.save(committedPaper);
       } catch (Exception e) {
           throw new RuntimeException("An error occurred while creating committed paper", e);
       }
    }

    public CommittedPaper getCommittedPaper(String id) {
        return committedPaperRepository.findById(id).orElseThrow(() -> new RuntimeException("Commit not found"));
    }

    public List<CommittedPaper> getCommittedPaperByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return committedPaperRepository.findByUserId(user);
    }
    public CommittedPaper getCommittedPaperByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return committedPaperRepository.findFirstByOrderId(order);
    }
    public List<CommittedPaper> getAllCommittedPaper() {
        return committedPaperRepository.findAll();
    }
}
