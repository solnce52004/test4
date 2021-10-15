package dev.example.entities;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

//@Embeddable //если  @EmbeddedId/@MapsId
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrganizationPK implements Serializable {
    private static final long serialVersionUID = 1L;

//    private String num;// если varchar(36)
    private UUID num;

    private Long departmentId; // если @IdClass
//    private Long department_id; //если @MapsId
}
