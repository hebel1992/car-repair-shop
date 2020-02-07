package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parts")
public class Part extends AbstractEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    @Min(1)
    private Double price;

    @Column(name = "quantity")
    @NotNull
    @Min(1)
    private Integer quantity;

    @ManyToOne
    private Order order;
}


