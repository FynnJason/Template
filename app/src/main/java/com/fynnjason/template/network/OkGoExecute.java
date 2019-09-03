package com.fynnjason.template.network;

import com.fynnjason.template.utils.LogUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

/**
 * 作者：FynnJason
 * 时间：2019-09-03
 * 备注：
 */

public class OkGoExecute {

    /**
     * 统一处理执行体
     *
     * @param api
     * @param okGoCallback
     * @return
     */
    private static StringCallback callBack(final String api, final OkGoCallback okGoCallback) {
        return new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e(api + "：" + response.body());
                try {
                    BaseBean baseBean = new Gson().fromJson(response.body(), BaseBean.class);
                    if (baseBean.getCode() == Api.Success) {
                        okGoCallback.success(response.body());
                    } else {
                        okGoCallback.error(baseBean.getCode(), baseBean.getMsg());
                    }
                } catch (Exception e) {
                    LogUtils.e(api + "：" + e.getLocalizedMessage());
                    okGoCallback.error(Api.JsonException, "数据解析错误");
                }
            }

            @Override
            public void onError(Response<String> response) {
                okGoCallback.error(response.code(), "服务器连接失败");
            }
        };
    }

    /**
     * get请求，不携带header
     *
     * @param api
     * @param params
     * @param okGoCallback
     */
    public static void getRequest(String api, HttpParams params, OkGoCallback okGoCallback) {
        OkGo.<String>get(api)
                .params(params)
                .execute(callBack(api, okGoCallback));
    }

    /**
     * get请求，携带header
     *
     * @param api
     * @param params
     * @param headers
     * @param okGoCallback
     */
    public static void getRequest(String api, HttpParams params, HttpHeaders headers, OkGoCallback okGoCallback) {
        OkGo.<String>get(api)
                .headers(headers)
                .params(params)
                .execute(callBack(api, okGoCallback));
    }

    /**
     * post请求，不携带header
     *
     * @param api
     * @param params
     * @param okGoCallback
     */
    public static void postRequest(String api, HttpParams params, OkGoCallback okGoCallback) {
        OkGo.<String>post(api)
                .params(params)
                .execute(callBack(api, okGoCallback));
    }

    /**
     * post请求，携带header
     *
     * @param api
     * @param params
     * @param headers
     * @param okGoCallback
     */
    public static void postRequest(String api, HttpParams params, HttpHeaders headers, OkGoCallback okGoCallback) {
        OkGo.<String>get(api)
                .headers(headers)
                .params(params)
                .execute(callBack(api, okGoCallback));
    }


}
