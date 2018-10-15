package cn.dagongniu.oax.https;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;


/**
 * 类描述：gosn util
 */

public class GsonUtils {



    public static <T> T decodeJSON(String jsonString, Class<T> cls) {
        Gson gson = new Gson();
        T model = gson.fromJson(jsonString, cls);
        return model;
    }

    public static <T> T changeGsonToBean(String result, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(result, cls);
        return t;
    }

    public static <T> List<T> gsontoList(String jsonString, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] model = gson.fromJson(jsonString, cls);
        return Arrays.asList(model);
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Class<T> type) {
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
        }.getType());
        return result;
    }

}