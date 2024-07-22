package org.swp391.valuationdiamond.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-zA-Z\\s]{1,255}$", message = "First name must contain only letters and spaces")
    String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]{1,255}$", message = "Last name must contain only letters and spaces")
    String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date birthday;

    @Pattern(regexp = "^(0)\\d{9,10}$", message = "Phone number must be in the format +84xxxxxxxxx or 0xxxxxxxxx")
    String phoneNumber;

    @Email
    String email;

    String address;
    String role;
    String status;
}
