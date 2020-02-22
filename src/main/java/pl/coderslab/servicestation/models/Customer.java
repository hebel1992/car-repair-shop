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
@Table(name = "customers")
public class Customer extends AbstractEntity {

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Past(message = "must be date from the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @NotNull(message = "phone number required")
    @Pattern(regexp = "\\d{9,15}", message = "9 characters minimum(only digits)")
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Size(max = 10000, message = "maximum 10000 characters")
    @Column(name = "note", columnDefinition = "text")
    private String note;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Vehicle> vehicles;
}
