package org.example.data.data_transfer_objects.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String token;
}
