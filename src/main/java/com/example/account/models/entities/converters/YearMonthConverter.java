package com.example.account.models.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Converter
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.format(DateTimeFormatter.ofPattern("MM-yyyy"));
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return YearMonth.parse(dbData, DateTimeFormatter.ofPattern("MM-yyyy"));
    }
}
