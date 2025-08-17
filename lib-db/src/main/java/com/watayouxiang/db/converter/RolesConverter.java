package com.watayouxiang.db.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

public class RolesConverter implements PropertyConverter<List<String>, String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) return null;

        return Arrays.asList(databaseValue.split(","));
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty == null || entityProperty.size() == 0) return null;

        int size = entityProperty.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(entityProperty.get(i));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
