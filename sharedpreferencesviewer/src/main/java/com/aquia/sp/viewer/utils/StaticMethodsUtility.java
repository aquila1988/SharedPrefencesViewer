package com.aquia.sp.viewer.utils;

import android.content.SharedPreferences;

/**
 * Created by yulong_wang on 2017/11/11 15:03.
 */

public final class StaticMethodsUtility {


    public static void putDataToSp(SharedPreferences sp, String type, String key, String v){
        if (type.equals(TypeConst.TYPE_STRING)){
            sp.edit().putString(key, v).commit();
        }
        else if (type.equals(TypeConst.TYPE_INT)){
            int value = Integer.parseInt(v);
            sp.edit().putInt(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_LONG)){
            long value = Long.parseLong(v);
            sp.edit().putLong(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_FLOAT)){
            float value = Float.parseFloat(v);
            sp.edit().putFloat(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_BOOLEAN)){
            boolean value = Boolean.valueOf(v);
            sp.edit().putBoolean(key, value).commit();
        }
    }

}
