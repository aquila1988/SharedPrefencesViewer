package com.aquia.sp.viewer.utils;

import java.util.HashMap;

/**
 * Created by yulong_wang on 2017/11/11 15:10.
 */

public final class TypeConst {
    private TypeConst() {
    }

    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_FLOAT = "float";

    private static HashMap<Integer, String> typeMap;

    public static HashMap<Integer, String> getTypeMap() {
        if (typeMap == null) {
            typeMap = new HashMap<>();
            int i = 0;
            typeMap.put(i++, TYPE_INT);
            typeMap.put(i++, TYPE_LONG);
            typeMap.put(i++, TYPE_BOOLEAN);
            typeMap.put(i++, TYPE_STRING);
            typeMap.put(i++, TYPE_FLOAT);
        }

        return typeMap;
    }

    public static String getObjectType(Object obj) {
        String type = "";
        if (obj instanceof String) {
            type = TYPE_STRING;
        } else if (obj instanceof Integer) {
            type = TYPE_INT;
        } else if (obj instanceof Long) {
            type = TYPE_LONG;
        } else if (obj instanceof Boolean) {
            type = TYPE_BOOLEAN;
        } else if (obj instanceof Float) {
            type = TYPE_FLOAT;
        }
        return type;

    }


}
