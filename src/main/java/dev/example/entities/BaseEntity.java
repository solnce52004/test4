package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Generated;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode//(of = {"createdAt", "updatedAt"})
@ToString
@Audited

public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "GENERATOR_ENHANCED_SEQUENCE")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS) //генерит время +3
    @Column(name="created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
//    @Generated(GenerationTime.ALWAYS)
    @UpdateTimestamp
    @Column(name="updated_at", insertable = false, columnDefinition = "TIMESTAMP")
    private Date updatedAt;
}
