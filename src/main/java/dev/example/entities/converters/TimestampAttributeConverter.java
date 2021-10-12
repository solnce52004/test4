package dev.example.entities.converters;

import dev.example.entities.BaseEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;

@Converter(autoApply = true)
public class TimestampAttributeConverter implements AttributeConverter<Timestamp, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Timestamp zoned) {

        return zoned == null
                ? null
                : Timestamp.from(zoned
                .toLocalDateTime()
                .atZone(ZoneId.of(BaseEntity.CURRENT_TIMEZONE))
                .toInstant()
        );
    }

    @Override
    public Timestamp convertToEntityAttribute(Timestamp timestamp) {
//        return timestamp;
        //TODO: почему приходит уже измененное относительно базы ?
        return timestamp == null
                ? null
                : Timestamp.from(timestamp
                .toInstant()
                .atZone(ZoneId.of(BaseEntity.CURRENT_TIMEZONE))
                .toInstant()
        );
    }
}
