package dev.example.entities;

import dev.example.entities.converters.StatusAttrConverter;
import dev.example.entities.embeddable.Status;
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
@EqualsAndHashCode(exclude = {"users"}, callSuper = true)
@ToString(exclude = {"users"}, callSuper = true)

public class Role extends BaseEntity {
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

    @Embedded
    @Convert(converter = StatusAttrConverter.class, attributeName = "is_actual")
    @AttributeOverrides(
            @AttributeOverride(
                    name = "isActualStatus",
                    column = @Column(name = "is_actual")
            )
    )
    private Status isActual;
}
