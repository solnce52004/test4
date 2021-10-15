package dev.example.entities;

import dev.example.entities.converters.StatusAttrConverter;
import dev.example.entities.embeddable.Status;
import dev.example.entities.event_listeners.PersistEntityListener;
import dev.example.entities.filters.DynamicUserFilter;
import dev.example.entities.interfaces.Auditable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"roles", "addresses"}, callSuper = true)
@ToString(callSuper = true)
//@Where(clause = "id=5000")
//@WhereJoinTable(clause = "")
//@Subselect("select *, count(u.id) as COUNTUSER5000 from users as u where id=5000")
//@Synchronize({"User"})
@EntityListeners(PersistEntityListener.class)
@Filter(name = DynamicUserFilter.LIMIT_BY_IS_ACTUAL)

public class User extends BaseEntity implements Auditable {
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
    private List<Role> roles = new ArrayList<>();


    @ManyToOne(
            optional = false,
            cascade = CascadeType.ALL, //не будем удалять адрес при удалении юзера
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "address_id")
    @Fetch(value = FetchMode.JOIN)
    @Type(type = "dev.example.entities.Address") //так можно задавть кастоные типы для поля
    private Address addresses;
    //Поведение HQL запросов при режиме загрузке FetchMode.JOIN, на первый взгляд, немного неожиданное. Вместо того, чтобы загрузить связанные коллекции, помеченные аннотацией @Fetch(FetchMode.JOIN), в одном запросе с корневыми сущностями, используя SQL оператор JOIN, HQL запрос транслируется в несколько SQL запросов по типа FetchMode.SELECT. Но в отличии от FetchMode.SELECT, при FetchMode.JOIN будет игнорироваться указанный тип загрузки (LAZY и EAGER) и все коллекции будут загружены сразу, а не при первом обращении в коде (поведение соответствующее типу EAGER).

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(
                    name = "isActualStatus",
                    column = @Column(name = "is_actual")
            )
    )
    @ColumnDefault("false")
//    @Generated(GenerationTime.INSERT)
    private Status isActual;

//    private Integer countUser5000; // from @Subselect

    @Formula("substr(username, 1, 3)")
    private String shortName;
}
