package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = { "users"})
@ToString(exclude = {"users"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private Integer level;

    // либо mappedBy, либо @JoinTable
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "roles"
            )
    @Fetch(value = FetchMode.JOIN)
    private List<User> users = new ArrayList<>();
}
