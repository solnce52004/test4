package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(
        exclude = {
                "roles",
                "addresses"
        }
)
@ToString(
        exclude = {
//                "roles",
//                "addresses"
        }
)
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL //не будем удалять роль при удалении юзера
            )
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @Fetch(value = FetchMode.JOIN)
//    @Fetch(value = FetchMode.SELECT)
//    @BatchSize(size = 10)
    private List<dev.example.entities.Role> roles = new ArrayList<>();

    @ManyToOne(
            optional = false,
            cascade = CascadeType.ALL //не будем удалять адрес при удалении юзера
    )
    @JoinColumn(name = "address_id")
    @Fetch(value = FetchMode.JOIN)
//Поведение HQL запросов при режиме загрузке FetchMode.JOIN, на первый взгляд, немного неожиданное. Вместо того, чтобы загрузить связанные коллекции, помеченные аннотацией @Fetch(FetchMode.JOIN), в одном запросе с корневыми сущностями, используя SQL оператор JOIN, HQL запрос транслируется в несколько SQL запросов по типа FetchMode.SELECT. Но в отличии от FetchMode.SELECT, при FetchMode.JOIN будет игнорироваться указанный тип загрузки (LAZY и EAGER) и все коллекции будут загружены сразу, а не при первом обращении в коде (поведение соответствующее типу EAGER).
    private Address addresses;
}
