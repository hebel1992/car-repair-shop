package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vehicle_arrival")
    private LocalDate vehicleArrival;

    @NotBlank
    @Column(name = "initial_diagnosis")
    private String initalDiagnosis;

    @NotNull
    @Column(name = "planned_repair_start")
    private LocalDate plannedRepairStart;

    @Column(name = "actual_repair_start")
    private LocalDate actualRepairStart;

    @Column(name = "ordered_parts")
    private String orderedParts;

    @Column(name = "costs_of_parts")
    private Double costsOfParts;

    @Column(name = "hours_of_service")
    private Integer hoursOfService;

    @Column(name = "price_of_service")
    private Double priceOfService;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @NotNull
    @JoinColumn(name = "vehicle_id")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
