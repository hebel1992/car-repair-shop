package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends AbstractEntity {

    @Column(name = "brand")
    @NotBlank
    private String brand;

    @Column(name = "model")
    @NotBlank
    private String model;

    @Column(name = "engine_size")
    private Double engineSize;

    @Range(min = 1920, max = 2020)
    @Column(name = "year_of_production")
    private Integer yearOfProduction;

    @NotBlank
    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "vin")
    private Long Vin;

    @NotNull
    @JoinColumn(name = "fuel_type")
    private FuelType fuelType;

    @Size(max = 10000, message = "maximum 10000 characters")
    @Column(name = "note", columnDefinition = "text")
    private String note;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private List<Order> orders;

}
