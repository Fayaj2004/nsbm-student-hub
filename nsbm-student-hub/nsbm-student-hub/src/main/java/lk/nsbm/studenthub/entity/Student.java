package lk.nsbm.studenthub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "batch is required")
    @Column(nullable = false)
    private String batch;

    @NotNull(message = "gpa is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "gpa must be >= 0.0")
    @DecimalMax(value = "4.0", inclusive = true, message = "gpa must be <= 4.0")
    @Column(nullable = false)
    private Double gpa;
}
