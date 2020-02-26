package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.servicestation.validators.MinAge;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends AbstractEntity {

    @NotNull
    @Size(min = 2, message = "min 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "min 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @MinAge(minAge = 16, message = "must be over 16")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank
    @Column(name = "address")
    private String address;

    @NotNull
    @Pattern(regexp = "\\d{9,15}", message = "9 digits minimum")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Min(value = 1, message = "minimum 1")
    @Column(name = "rate_per_hour")
    private Double ratePerHour;

    @Size(max = 10000, message = "maximum 10000 characters")
    @Column(name = "note", columnDefinition = "text")
    private String note;

    @ManyToMany(mappedBy = "employees")
    private Set<Order> orders;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.REMOVE)
    private User user;

    @PreRemove
    private void preRemove() {
        orders.forEach(o -> o.getEmployees().remove(this));
    }
}
