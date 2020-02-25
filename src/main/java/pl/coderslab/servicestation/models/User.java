package pl.coderslab.servicestation.models;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.servicestation.validationGroups.UserGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="username" ,nullable = false, unique = true)
    @Size(min = 5, max = 15)
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "min 8 characters")
    private String password;

    private int enabled;

    @Size(min = 1, message = "Role cannot be null")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @NotNull(groups = {UserGroup.class})
    @OneToOne
    private Employee employee;
}
