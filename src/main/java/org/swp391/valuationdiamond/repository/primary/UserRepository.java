package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.Role;
import org.swp391.valuationdiamond.entity.primary.Status;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> getUsersByRole(Role role);

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByUserId(String user);
    User findByUserIdAndPassword(String userId, String password);
    List<User> findByStatus(Status status);
    List<User> findByStatusAndRole(Status status, Role role);
    User findByUserIdAndStatus(String userId, Enum<Status> status);


}
