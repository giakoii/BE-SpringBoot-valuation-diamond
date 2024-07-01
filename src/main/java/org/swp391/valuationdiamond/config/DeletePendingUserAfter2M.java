package org.swp391.valuationdiamond.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.swp391.valuationdiamond.entity.primary.PendingUser;
import org.swp391.valuationdiamond.repository.primary.PendingUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DeletePendingUserAfter2M {

    @Autowired
    private PendingUserRepository pendingUserRepository;

    @Scheduled(fixedRate = 120000)
    public String deletePendingUserAfter2Minutes() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(2);
        List<PendingUser> unConfirmedUsers = pendingUserRepository.findAllByOtpCreationTimeBefore(cutoffTime);
        if (unConfirmedUsers.isEmpty()) {
            return "No pending user to delete";
        }
        for (PendingUser user : unConfirmedUsers) {
            pendingUserRepository.delete(user);
        }
        return "Deleted " + unConfirmedUsers.size() + " pending users";
    }

}
