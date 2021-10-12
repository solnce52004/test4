package dev.example.entities;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(of = {"createdAt", "updatedAt"})
@ToString
public class BaseEntity {
    public static final String CURRENT_TIMEZONE = "Europe/Moscow";
//    public static final String CURRENT_TIMEZONE = "Asia/Ho_Chi_Minh";//test

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "GENERATOR_ENHANCED_SEQUENCE")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS) //генерит время +3
    @Column(name="created_at", updatable = false, insertable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name="updated_at", updatable = false, insertable = false)
    private Date updatedAt;
}
