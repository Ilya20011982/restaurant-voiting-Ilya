package ru.project.restaurantvotingilya.web.converter;

import lombok.NonNull;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatters {
    @Component
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(@NonNull String text, Locale locale) {
            return StringUtils.hasLength(text) ? LocalDate.parse(text) : null;
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}
