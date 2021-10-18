//package dev.example.entities.envers.tracking;
//
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.hibernate.envers.NotAudited;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//
//public class ModifiedEntityTypeEntity {
//
//    @Id
//    @GeneratedValue
//    private Integer id;
//
//    @ManyToOne
//    private CustomTrackingRevisionEntity revision;
//
//    private String entityClassName;
//
//    public ModifiedEntityTypeEntity(CustomTrackingRevisionEntity revision, String entityClassName) {
//        this.entityClassName = entityClassName;
//        this.revision = revision;
//    }
//}
//
//
