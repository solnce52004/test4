package dev.example.entities;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "organization")
@ToString(exclude = "organization")
@Audited
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "department.organizations",
                attributeNodes = @NamedAttributeNode(value = "organization")
        ),
        @NamedEntityGraph(
                name = "department.organizations.users",
                attributeNodes = {
                        @NamedAttributeNode(
                                value = "organization",
                                subgraph = "users-subgraph"
                        )
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "users-subgraph",
                                attributeNodes = @NamedAttributeNode(value = "users")
                        )
                }
        ),
})

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
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Organization> organization = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "organizations", joinColumns=@JoinColumn(name="department_id"))
    @Column(name = "title")
    private Set<String> orgTitles = new HashSet<>();
}
