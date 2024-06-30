package org.swp391.valuationdiamond.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    String userId;
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    String password;
    String firstName;
    String lastName;
    Date birthday;
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    String phoneNumber;
    String email;
    String address;
    String role;
}
