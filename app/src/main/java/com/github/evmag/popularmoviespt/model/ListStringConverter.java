package com.github.evmag.popularmoviespt.model;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class ListStringConverter {
    @TypeConverter
    public String listStringToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");

        for (String string : list) {
            sb.append(string + "|");
        }

        return sb.toString();
    }

    @TypeConverter
    public List<String> stringToList(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        return Arrays.asList(string.split("\\s*|\\s*"));
    }
}
