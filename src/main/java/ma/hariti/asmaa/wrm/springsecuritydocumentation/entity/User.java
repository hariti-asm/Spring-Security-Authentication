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
@Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;
}
