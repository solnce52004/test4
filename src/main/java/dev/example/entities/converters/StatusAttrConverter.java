package dev.example.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusAttrConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean isActual) {
        return Boolean.TRUE.equals(isActual) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer isActual) {
        return isActual > 0;
    }
}
