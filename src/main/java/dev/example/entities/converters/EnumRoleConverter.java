package dev.example.entities.converters;

import dev.example.entities.enams.EnumRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * autoApply = true
 * определяется целевой класс в записи <EnumRole, String>
 * будет применяться этот конвертер при любых сохранениях в базу любых обектов EnumRole
 */
@Converter(autoApply = true)
public class EnumRoleConverter implements AttributeConverter<EnumRole, String> {

    @Override
    public String convertToDatabaseColumn(EnumRole attribute) {
        return attribute.name().toLowerCase();
    }

    @Override
    public EnumRole convertToEntityAttribute(String dbData) {
        return EnumRole.valueOf(dbData.toUpperCase());
    }
}
