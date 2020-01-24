package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "min 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @Past(message = "must be date from the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Pattern(regexp = "\\d{9,15}", message = "9 characters minimum(only digits)")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Size(max = 1000, message = "maximum 1000 characters")
    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "customer")
    private List<Vehicle> vehicles;

    @PreRemove
    private void preRemove() {
        vehicles.forEach(child -> child.setCustomer(null));
    }
}
