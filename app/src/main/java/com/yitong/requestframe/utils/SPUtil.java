package com.yitong.requestframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences存储各种文件
 */

public class SPUtil {
    /**
     * 保存在手机的文件名
     */
    private static final String FILE_NAME="shareData";

    /**
     * 保存文件
     */
    public static void put(Context context,String key,Object object){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(object instanceof String){
            editor.putString(key, (String) object);
        }else if(object instanceof Integer){
            editor.putInt(key, (Integer) object);
        }else if(object instanceof Boolean){
            editor.putBoolean(key, (Boolean) object);
        }else if(object instanceof Float){
            editor.putFloat(key, (Float) object);
        }else if(object instanceof Long){
            editor.putLong(key, (Long) object);
        }else{
            editor.putString(key,object.toString());
        }
        editor.apply();
    }

    /**
     * 查找文件
     */
    public static Object get(Context context,String key,Object defaultValus){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

        if(defaultValus instanceof String){
            return sp.getString(key, (String) defaultValus);
        }else if(defaultValus instanceof Integer){
            return sp.getInt(key, (Integer) defaultValus);
        }else if(defaultValus instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultValus);
        }else if(defaultValus instanceof Float){
            return sp.getFloat(key, (Float) defaultValus);
        }else if(defaultValus instanceof Long){
            return sp.getLong(key, (Long) defaultValus);
        }else{
            return sp.getString(key, (String) defaultValus);
        }
    }

    /**
     * 删除某个值
     */
    public static void delete(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(key);
        editor.apply();

    }

    /**
     * 删除除手机号之外的值
     */
    public static void deleteButPhone(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove("userId");//用户id
        //editor.remove("loginPhone");//用户手机号码
        editor.remove("randumNum");//生成的随机验证码
        editor.remove("sendTime");//验证码发送时间
        editor.remove("token");//token
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.apply();
    }

}
