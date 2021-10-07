package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@DynamicUpdate
@DynamicInsert
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "num_building")
    private Integer numBuilding;

    @OneToMany(
            mappedBy = "addresses",//название поля класса User
            fetch = FetchType.LAZY
    )
    // !!! Associations marked as mappedBy must not define database mappings like @JoinTable or @JoinColumn
//    @JoinColumn(
//            name = "address_id",
//            referencedColumnName = "id"
//    )
    @Fetch(value = FetchMode.JOIN)
    //В случае, когда вы хотите добавить к связи @OneToMany еще одну сущность, выгоднее использовать Bag, т.к. он для этой операции не требует загрузки всех связанных сущностей.
    private List<User> users = new ArrayList<>();
}
