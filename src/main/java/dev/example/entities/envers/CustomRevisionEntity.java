package dev.example.entities.envers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "REVINFO")
@RevisionEntity(CustomRevisionEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name="REV")),
        @AttributeOverride(name = "timestamp", column = @Column(name="REVTSTMP"))
})
//TODO: почему идея не видит ид родителького класса

public class CustomRevisionEntity extends DefaultRevisionEntity {
    private static final Long serialVersionUID = 1L;

//    @AttributeOverride(name = "id", column = @Column(name="REV"))
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @RevisionNumber
//    @Column(name="REV")
//    private int id;
//
 //    @AttributeOverride(name = "timestamp", column = @Column(name="REVTSTMP"))
//    @RevisionTimestamp
//    @Column(name="REVTSTMP")
//    private long timestamp;

    private String username;
}
