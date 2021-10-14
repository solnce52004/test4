package dev.example.entities.embeddable;

import dev.example.entities.converters.StatusAttrConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
//@Convert(converter = StatusAttrConverter.class, attributeName = "isActualStatus")

public class Status {
    @Convert(converter = StatusAttrConverter.class)
    private Boolean isActualStatus;
}
