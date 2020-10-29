package com.imooc.mysell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author lzj
 * @Date 2020/9/30 18:17
 * @Version 1.0
 */
public class JsonUtils {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();
        return gson.toJson(object);

    }
}
