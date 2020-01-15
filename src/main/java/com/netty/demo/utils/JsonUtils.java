package com.netty.demo.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static List<Map<String, Object>> json2List(String json) {

        if (json == null || "".equals(json) || json.length() < 2)
            return new ArrayList<Map<String, Object>>();
        json = json.substring(1, json.length() - 1);

        String[] objects = json.split("}");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (String object : objects) {
            if (object.startsWith(",")) {
                object = object.substring(2, object.length());
            } else {
                object = object.substring(1, object.length());
            }
            Map<String, Object> map = new HashMap<String, Object>();

            String[] keyValues = object.split(",");
            for (String keyValue : keyValues) {
                String[] key = keyValue.split(":");
                map.put(key[0].replaceAll("\"", ""), key[1].replaceAll("\"", ""));
            }
            list.add(map);
        }
        return list;
    }


    public static <T> String toJson(T obj) {
        return JSONObject.toJSONString(obj);
    }

    public static <T> T parse(String json, Class<T> tClass) {
        return JSONObject.parseObject(json, tClass);
    }
}
