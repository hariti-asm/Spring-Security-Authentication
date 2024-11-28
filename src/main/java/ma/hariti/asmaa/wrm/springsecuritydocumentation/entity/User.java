package ma.hariti.asmaa.wrm.springsecuritydocumentation.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionId;

import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    private boolean enabled = true;
}
