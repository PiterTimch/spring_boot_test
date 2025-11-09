package org.example.data.data_transfer_objects.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTO {
    @NotBlank(message = "Email не може бути порожнім")
    @Email(message = "Некоректний формат email. Приклад: e@e.e")
    private String email;
}
