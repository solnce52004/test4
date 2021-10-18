package dev.example.entities;

import dev.example.entities.converters.ZonedDateTimeAttributeConverter;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "users", callSuper = true)
@ToString(exclude = "users", callSuper = true)
@DynamicUpdate
@DynamicInsert
@Audited

public class Address extends BaseEntity {

    //@Temporal should only be set on a java.util.Date or java.util.Calendar
    //Temporal will not be converted
//    @Temporal(TemporalType.TIMESTAMP)

    //Эти генераторы ставят время системное (или -3 UTC - если в url бд есть такой параметр)
//    @CreationTimestamp //запишется именно значение генератора вместо сеттера
//    @UpdateTimestamp // только один тип генератора можно использовать для одного поля

    @Convert(
            converter = ZonedDateTimeAttributeConverter.class,
            attributeName = "verified_at"
    )
    @Column(name = "verified_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime verifiedAt;


    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "num_building")
    @ColumnTransformer( //bed practice!
            read = "num_building / 10",
            write = "? * 10"
    )
    private Integer numBuilding;

    @OneToMany(
            mappedBy = "addresses",//название поля класса User
            fetch = FetchType.LAZY
//            ,
//            cascade = CascadeType.ALL
    )
    @Fetch(value = FetchMode.JOIN)
    private Set<User> users = new HashSet<>();
    //В случае, когда вы хотите добавить к связи @OneToMany еще одну сущность, выгоднее использовать Bag, т.к. он для этой операции не требует загрузки всех связанных сущностей.
}
