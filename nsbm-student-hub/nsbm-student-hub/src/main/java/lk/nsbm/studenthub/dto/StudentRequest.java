package lk.nsbm.studenthub.dto;

import jakarta.validation.constraints.*;

public record StudentRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
        @NotBlank(message = "batch is required") String batch,
        @NotNull(message = "gpa is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "gpa must be >= 0.0")
        @DecimalMax(value = "4.0", inclusive = true, message = "gpa must be <= 4.0")
        Double gpa
) {}
