package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @Past(message = "must be date from the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotBlank
    @Column(name = "address")
    private String address;

    @Pattern(regexp = "\\d{9,15}", message = "9 digits minimum")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Min(value = 1, message = "minimum 1")
    @Column(name = "rate_per_hour")
    private Double ratePerHour;

    @Column(name = "note")
    private String note;
}
