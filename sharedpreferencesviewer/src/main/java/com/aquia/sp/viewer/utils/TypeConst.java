package com.aquia.sp.viewer.utils;

/**
 * Created by yulong_wang on 2017/11/11 15:10.
 */

public final class TypeConst {
    private TypeConst(){}

//    public static final int TYPE_INT = 0X1;
//    public static final int TYPE_LONG = 0X2;
//    public static final int TYPE_BOOLEAN = 0X3;
//    public static final int TYPE_STRING = 0X4;
//    public static final int TYPE_FLOAT = 0X5;
//    public static final int TYPE_STRING_SET = 0X6;
//    public static final int TYPE_ = 0X7;
//    public static final int TYPE_ = 0X8;

    public static String getObjectType(Object obj){
        String type = "";
        if (obj instanceof String){
            type = "String";
        }
        else if (obj instanceof Integer){
            type = "int";
        }
        else if (obj instanceof Long){
            type = "long";
        }
        else if (obj instanceof Boolean){
            type = "bool";
        }
        else if (obj instanceof Float){
            type = "float";
        }
        return type;

    }



}
