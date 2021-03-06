package dev.example.entities;

import dev.example.entities.converters.EnumRoleConverter;
import dev.example.entities.converters.StatusAttrConverter;
import dev.example.entities.embeddable.Status;
import dev.example.entities.enams.EnumRole;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"users"}, callSuper = true)
@ToString(callSuper = true)
@DynamicUpdate
@DynamicInsert
@Log4j2
@Audited

public class Role extends BaseEntity {
    public static final String COLUMN_TITLE_NAME = "title";

    //    private String title = UserRole.QUEST.getRole(); //если решим работать как со строкой
//    @Enumerated(value = EnumType.STRING) // если используем конвертер - не указваем аннотацию!

    // можно не указывать конвертер, у которого autoApply = true и в параметрах класс аттрибута
    // такой конвертер будет действать для всех атрибутов такого типа (EnumRole) для всех моделей!!!
    // везде в коде - upper, в базу упадет - lower
//    @Enumerated
    @Convert(converter = EnumRoleConverter.class)
    @Column(name = COLUMN_TITLE_NAME, nullable = false, columnDefinition = "VARCHAR(255)")
    private EnumRole title = EnumRole.QUEST;

    @Column(name = "level")
    private Integer level;

    // либо mappedBy, либо @JoinTable
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "roles"
            ,cascade = CascadeType.ALL
    )
    @Fetch(value = FetchMode.JOIN)
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(
                    name = "isActualStatus",
                    column = @Column(name = "is_actual")
            )
    )
    private Status isActual;

    @PostPersist
    public void notifyAdmin(){
        log.info("NOTIFY ADMIN: entity: {}", this);
    }
}
