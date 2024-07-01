package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swp391.valuationdiamond.entity.primary.PendingUser;

import java.time.LocalDateTime;
import java.util.List;

public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {
    PendingUser findByUserId(String userId);
    PendingUser findByEmail(String email);
    void deleteByUserId(String userId);
    List<PendingUser> findAllByOtpCreationTimeBefore(LocalDateTime cutoffTime);
}
