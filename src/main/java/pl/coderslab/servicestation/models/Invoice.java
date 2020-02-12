package pl.coderslab.servicestation.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Lob
    @Column(name = "code")
    private String code;

    @ManyToOne
    private Order order;

    @PrePersist
    public void createCode(){
        code = Base64.getEncoder().encodeToString(file);
    }
}
