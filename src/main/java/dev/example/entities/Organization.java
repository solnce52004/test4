package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "organizations")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@IdClass(OrganizationPK.class)
public class Organization {

    ////////////////////
//    @EmbeddedId
//    private OrganizationPK embeddedId;

    ////////////////////
    // with @IdClass!!!

    //1
    // - в классе ключа изменить тип параметра на UUID!
    // - можно не указывать генератор - будет исп-ся по умолчанию uuid2, если тип параметра UUID
    // uuid мсохранится в виде "00000000  69 4A 0C CC CB 00 43 62 B3 60 15 84 D7 58 3E 18    iJ.ÌË.Cb³`..×X>."
//    @Id
////    @Column(name = "num", nullable = false, columnDefinition = "BINARY(16)")
//    @GeneratedValue
//    private UUID num;

    //2
    // - в классе ключа изменить тип параметра на UUID!
    // uuid сохранится в виде "ada8b682-67e3-433e-a807-990ae19bab69"
    @Id
    @Column(name = "num", nullable = false, columnDefinition = "varchar(36)")//"uuid-char"
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID num;

    // 3
    // - в классе ключа изменить тип параметра на String!
    // - @Type - если varchar и UUIDGenerator/uuid/guid
//    @Id
//    @Column(name = "num", nullable = false, columnDefinition = "varchar(36)")
//    @GeneratedValue(generator = "guid")
//    @GenericGenerator(
//            name = "guid",
////            strategy = "uuid" //DEPRECATED (32)
////            strategy = "guid" //DEPRECATED (36)
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    private String num;

    @Id
    @Column(name = "department_id", nullable = false)
    private Long departmentId;
    //////////////////////

    @Column(name = "title", nullable = true, length = 255)
    private String title;

    @Column(name = "address_id", nullable = true)
    private Long addressId;

    @ManyToOne
//            (
//            optional = false,
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//    )
    @Fetch(value = FetchMode.JOIN)
    @JoinColumn( //@MapsId не надо указывть
            name = "department_id",
            referencedColumnName = "id",
            nullable = false,
            insertable = false,
            updatable = false
    )
//    @MapsId("departmentId") //если часть составного ключа - внешний (@JoinColumn не надо указывть)
    // позволяет каскадное сохранение
    private Department department;
}
