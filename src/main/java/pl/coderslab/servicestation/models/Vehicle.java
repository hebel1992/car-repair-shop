package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    @NotBlank
    private String brand;

    @Column(name = "model")
    @NotBlank
    private String model;

    @Column(name = "engine_size")
    private Double engineSize;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
