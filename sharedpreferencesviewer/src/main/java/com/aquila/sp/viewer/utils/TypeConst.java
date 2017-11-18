package com.aquila.sp.viewer.utils;

import java.util.HashMap;


public final class TypeConst {
    private TypeConst() {
    }

    public static final String TYPE_INT = "Int";
    public static final String TYPE_LONG = "Long";
    public static final String TYPE_BOOLEAN = "Boolean";
    public static final String TYPE_STRING = "String";
    public static final String TYPE_FLOAT = "Float";

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
