package dev.example.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "departments")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class Department {
    @Id
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "title", nullable = false, length = 255, unique = true)
    private String title;

    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL
    )
    private Set<Organization> organization;
}
