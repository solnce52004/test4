//package dev.example.entities.envers.tracking;
//
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.envers.*;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@RevisionEntity(CustomEntityTrackingRevisionListener.class)
////@Table(name = "REVINFO")
//@NoArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode(callSuper = true)
//
//public class CustomTrackingRevisionEntity extends DefaultTrackingModifiedEntitiesRevisionEntity {
//
//    @Id
//    @GeneratedValue
//    @RevisionNumber
////    @Column(name="REV")
//    private int customId;
//
//    @RevisionTimestamp
////    @Column(name="REVTSTMP")
//    private long customTimestamp;
//
//    @OneToMany(mappedBy = "revision", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
////    @OneToMany(mappedBy = "REVINFO", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private final Set<ModifiedEntityTypeEntity> modifiedEntityTypes = new HashSet<>();
//
//    public void addModifiedEntityType(String entityClassName) {
//        modifiedEntityTypes.add(new ModifiedEntityTypeEntity(this, entityClassName));
//    }
//
//    private String username;
//}
//
//
