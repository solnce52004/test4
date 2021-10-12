package dev.example.entities.converters;

import dev.example.entities.BaseEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zoned) {

        return zoned == null
                ? null
                : Timestamp.from(zoned
                        .toLocalDateTime()
                        .atZone(ZoneId.of(BaseEntity.CURRENT_TIMEZONE))
                        .toInstant()
        );
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp timestamp) {

        return timestamp == null
                ? null
                : timestamp
                .toInstant()
                .atZone(ZoneId.of(BaseEntity.CURRENT_TIMEZONE));
    }
}
