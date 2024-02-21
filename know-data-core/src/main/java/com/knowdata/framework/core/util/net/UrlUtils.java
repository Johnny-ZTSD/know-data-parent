package com.knowdata.framework.core.util.net;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2022/12/12 11:53
 * @description ...
 */
public class UrlUtils {
    public static final String PARAM_PROTOCOL = "protocol";
    public static final String PARAM_HOST = "host";
    public static final String PARAM_PORT = "port";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_QUERY = "query";

    /**
     * 解析 url
     * @param url
     *  eg: "http://127.0.0.1:9200/_opendistro/_sql?format=json";
     * @throws IOException
     * @return map
     */
    public static Map<String, String> parseUrl(String url) throws IOException {
//        String httpUrl = "http://127.0.0.1:9200/_opendistro/_sql?format=json";
        Map<String, String> map = new LinkedHashMap<>();
        URL urlObject = new URL(url);
        map.put(PARAM_PROTOCOL, urlObject.getProtocol());
        map.put(PARAM_HOST, urlObject.getHost());
        map.put(PARAM_PORT, String.valueOf(urlObject.getPort()));
        map.put(PARAM_PATH, urlObject.getPath());
        map.put(PARAM_QUERY, urlObject.getQuery());

        //认证信息
        //map.put("authority", urlObject.getAuthority());
        //map.put("userInfo", urlObject.getUserInfo());
        //引用信息
        //map.put("ref", urlObject.getRef());
        //map.put("content", urlObject.getContent());
//        Print.print(map);
        return map;
    }

    /**
     * 解析url中的参数
     * @description
     *  String urlString = "https://example.com/search?q=java&sort=price";
     *  Map<String, String> params = parseUrlParams(urlString);
     *  for (Map.Entry<String, String> entry : params.entrySet()) {
     *      System.out.println(entry.getKey() + " = " + entry.getValue());//q = java<br>sort = price
     *  }
     */
    public static Map<String, String> parseUrlParams(String urlString) {
        Map<String, String> params = new HashMap<>();
        try {
            URL url = new URL(urlString);
            String query = url.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = idx > 0 ? pair.substring(0, idx) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
                params.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
