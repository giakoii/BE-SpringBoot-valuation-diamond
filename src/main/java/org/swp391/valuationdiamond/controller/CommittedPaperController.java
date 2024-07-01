package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.swp391.valuationdiamond.dto.CommittedPaperDTO;
import org.swp391.valuationdiamond.entity.primary.CommittedPaper;
import org.swp391.valuationdiamond.service.CommittedPaperServiceImp;

import java.util.List;

@RestController
@RequestMapping("/committed_Paper")
public class CommittedPaperController {
    @Autowired
    CommittedPaperServiceImp committedPaperServiceImp;
    @PostMapping("/create")
    CommittedPaper createCommittedPaper(@RequestBody CommittedPaperDTO committedPaperDTO) {
        return committedPaperServiceImp.createCommittedPaper(committedPaperDTO);
    }
    @GetMapping("/getCommittedPaper")
    List<CommittedPaper> getCommittedPaper (){
        {
            return committedPaperServiceImp.getAllCommittedPaper();
        }
    }
    @GetMapping("/getCommittedPaper/{committedId}")
    CommittedPaper getCommittedPaper(@PathVariable String committedId) {
        return committedPaperServiceImp.getCommittedPaper(committedId);
    }
    @GetMapping("/getCommittedPaperByUserId/{userId}")
    public List<CommittedPaper> getCommittedPaperByUserId(@PathVariable("userId") String userId) {
        try {
            return committedPaperServiceImp.getCommittedPaperByUserId(userId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the evaluation results");
        }
}
    @GetMapping("/getCommittedPaperByOrderId/{orderId}")
    public CommittedPaper getCommittedPaperByOrderId(@PathVariable("orderId") String orderId) {
        try {
            return committedPaperServiceImp.getCommittedPaperByOrderId(orderId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the evaluation results");
        }
    }
}
