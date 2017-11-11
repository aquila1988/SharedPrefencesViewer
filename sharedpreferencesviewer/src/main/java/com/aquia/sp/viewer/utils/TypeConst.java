package com.aquia.sp.viewer.utils;

/**
 * Created by yulong_wang on 2017/11/11 15:10.
 */

public final class TypeConst {
    private TypeConst(){}

    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_FLOAT = "float";

    public static String getObjectType(Object obj){
        String type = "";
        if (obj instanceof String){
            type = TYPE_STRING;
        }
        else if (obj instanceof Integer){
            type = TYPE_INT;
        }
        else if (obj instanceof Long){
            type = TYPE_LONG;
        }
        else if (obj instanceof Boolean){
            type = TYPE_BOOLEAN;
        }
        else if (obj instanceof Float){
            type = TYPE_FLOAT;
        }
        return type;

    }



}
