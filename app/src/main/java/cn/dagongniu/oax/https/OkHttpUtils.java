package cn.dagongniu.oax.https;

import android.app.Activity;
import android.os.Handler;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.dagongniu.oax.OAXApplication;
import okhttp3.Call;
import okhttp3.Response;


/**
 * OkHttp 工具
 */
public class OkHttpUtils {

    private static OkHttpUtils okHttpUtils;
    private KProgressHUD dialog;
    private Activity activity;

    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            okHttpUtils = new OkHttpUtils();
        }
        return okHttpUtils;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void get(String url, String cacheKey, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        HttpDebugLogUtils.prinlnLog("get---request---" + url);
        OkGo.get(url)     // 请求方式和请求url
                // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");

                    }
                });


    }

    public void get(String url, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        HttpDebugLogUtils.prinlnLog("get---request---" + url);
        OkGo.get(url)     // 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void getMap(String url, HashMap<String, String> params, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String param = "?";
        for (String msg : params.keySet()) {
            param = param + msg + "=" + params.get(msg) + "&";
        }
        param = param.substring(0, param.length() - 1);
        HttpDebugLogUtils.prinlnLog("get---request---" + url + param);
        OkGo.get(url + param)     // 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void getMap(String url, HashMap<String, String> params, final OnBaseDataListener<String> onBaseDataListener, Activity activity) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String param = "?";
        for (String msg : params.keySet()) {
            param = param + msg + "=" + params.get(msg) + "&";
        }
        param = param.substring(0, param.length() - 1);
        HttpDebugLogUtils.prinlnLog("get---request---" + url + param);
        OkGo.get(url + param).tag(activity)     // 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void getMap(String url, HashMap<String, String> params, final OnBaseDataListener<String> onBaseDataListener, String activity) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String param = "?";
        for (String msg : params.keySet()) {
            param = param + msg + "=" + params.get(msg) + "&";
        }
        param = param.substring(0, param.length() - 1);
        HttpDebugLogUtils.prinlnLog("get---request---" + url + param);
        OkGo.get(url + param).tag(activity)     // 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }


    public void getMap(String url, HashMap<String, String> params, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        String param = "?";
        for (String msg : params.keySet()) {
            param = param + msg + "=" + params.get(msg) + "&";
        }
        param = param.substring(0, param.length() - 1);
        HttpDebugLogUtils.prinlnLog("get---request---" + url + param);
        OkGo.get(url + param).tag(activity)    // 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void get(String url, Activity activty, final OnBaseDataListener<String> onBaseDataListener, final RequestState state) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        if (state == RequestState.STATE_DIALOG || state == RequestState.STATE_ALL_SCREEN_AND_DIALOG)
            showDialog(activty);
        HttpDebugLogUtils.prinlnLog("get---request---" + url);
        OkGo.get(url).tag(activty)// 请求方式和请求url// 请求的 tag, 主要用于取消对应的请求
                .cacheKey(url + "uid")
                // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        // s 即为所需要的结果
                        // 模拟网络请求的延时
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (state == RequestState.STATE_DIALOG || state == RequestState.STATE_ALL_SCREEN_AND_DIALOG)
                                    dismissDialog();
                                onBaseDataListener.onNewData(s);
                            }
                        }, 200);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (state == RequestState.STATE_DIALOG || state == RequestState.STATE_ALL_SCREEN_AND_DIALOG)
                            dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });
    }

    public void post(String url, HashMap<String, String> map, String cacheKey, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        PostRequest post = OkGo.post(url);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            post.params(param, map.get(param));
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request----" + url + reQuestParam);
        post.isMultipart(true).cacheKey(cacheKey)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError(e.toString());
                    }
                });
    }

    public void post(String url, HashMap<String, String> map, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        PostRequest post = OkGo.post(url);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            post.params(param, map.get(param));
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        post.isMultipart(true).cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void postJson(String url, String json, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        PostRequest post = OkGo.post(url);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String reQuestParam = "?";
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jsonObject.getString(key);
                post.params(key, String.valueOf(jsonObject.get(key)));
                reQuestParam = reQuestParam + key + "=" + value + "&";
            }
            reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
            HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post.isMultipart(true).cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });
    }

    public void post(String url, HashMap<String, String> map, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url).tag(activity);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            post.params(param, map.get(param));
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        post.isMultipart(true).cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void postJson(String url, String json, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url).tag(activity);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String reQuestParam = "?";
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jsonObject.getString(key);
                post.params(key, String.valueOf(jsonObject.get(key)));
                reQuestParam = reQuestParam + key + "=" + value + "&";
            }
            reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
            HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post.isMultipart(true).cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });
    }

    public void postJsonFile(String url, String json, List<File> files, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url).tag(activity);
        String reQuestParam = "&";

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jsonObject.getString(key);
                post.params(key, String.valueOf(jsonObject.get(key)));
                reQuestParam = reQuestParam + key + "=" + value + "&";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for (File file : files) {
//            post.params(file.getName(), file);
//        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        post.cacheKey(url + "uid")
                .isMultipart(true)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }


    public void delete(String url, HashMap<String, String> map, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        DeleteRequest delete = OkGo.delete(url + reQuestParam);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        delete.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void delete(String url, HashMap<String, String> map, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        DeleteRequest delete = OkGo.delete(url + reQuestParam).tag(activity);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        delete.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void postFile(Activity activity, String url, HashMap<String, String> map, List<File> files, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            post.params(param, map.get(param));
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        for (File file : files) {
            post.params(file.getName(), file);
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        post.cacheKey(url + "uid")
                .isMultipart(true).cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void postFile(String url, List<File> files, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        PostRequest post = OkGo.post(url);
        for (File file : files) {
            post.params("files", file);
        }
        HttpDebugLogUtils.prinlnLog("post---request--" + url);
        post.cacheKey(url + "uid")
                .isMultipart(true).cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void postFile(String url, List<File> files, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url);
        for (File file : files) {
            post.params(file.getName(), file);
        }
        HttpDebugLogUtils.prinlnLog("post---request--" + url);
        post.cacheKey(url + "uid")
                .isMultipart(true).cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }



    //上传单一文件
    public void postSingleFile(String url, HashMap<String, String> map, File file, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        PostRequest post = OkGo.post(url);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            post.params(param, map.get(param));
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        /*for (File file : files) {
            post.params(file.getName(), file);
        }*/
        //fileBody
        post.params("uploadFile", file);
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        post.cacheKey(url + "uid")
                .isMultipart(true).cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void put(String url, HashMap<String, String> map, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        PutRequest put = OkGo.put(url + reQuestParam);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        put.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void put(String url, HashMap<String, String> map, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        showDialog(activity);
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        PutRequest put = OkGo.put(url + reQuestParam);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        put.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });
    }

    public void getTemp(String url, HashMap<String, String> map, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        GetRequest getRequest = OkGo.get(url + reQuestParam);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        getRequest.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }

    public void getTemp(String url, HashMap<String, String> map, Activity activity, final OnBaseDataListener<String> onBaseDataListener) {
        if (PrefUtils.getNetState(OAXApplication.getContext(), 0) == 0) {
            onBaseDataListener.onError("网络异常");
            return;
        }
        String reQuestParam = "?";
        for (String param : map.keySet()) {
            reQuestParam = reQuestParam + param + "=" + map.get(param) + "&";
        }

        showDialog(activity);
        reQuestParam = reQuestParam.substring(0, reQuestParam.length() - 1);
        GetRequest getRequest = OkGo.get(url + reQuestParam);
        HttpDebugLogUtils.prinlnLog("post---request--" + url + reQuestParam);
        getRequest.cacheKey(url + "uid")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        onBaseDataListener.onNewData(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissDialog();
                        onBaseDataListener.onError("服务器异常");
                    }
                });

    }


    /**
     * 网络加载弹框
     */
    public void showDialog(Activity activity) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    //                    .setLabel("正在加载...")
                    //                    .setDetailsLabel("Downloading data")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    /**
     * 关闭网络加载弹框
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
