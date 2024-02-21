package com.knowdata.framework.siteserver.common.util;

import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/4/21 13:09
 * @description 日志中排出的 urls
 */
public class LogExcludedPathsUtil {
    public static Set<String> LOG_EXCLUDED_PATHS = null;
    private static List<String> LOG_EXCLUDED_PATHS_LIST = null;

    static {
        LOG_EXCLUDED_PATHS = Collections.unmodifiableSet(
                new HashSet<>(
                        Arrays.asList("/actuator")
                )
        );
        LOG_EXCLUDED_PATHS_LIST = new ArrayList<>(LOG_EXCLUDED_PATHS.size());
        LOG_EXCLUDED_PATHS_LIST.addAll(LOG_EXCLUDED_PATHS);
    }

    public static void main(String[] args) {
        System.out.println("http://127.0.0.1:18200/test/99".contains("/test/"));
    }

    /**
     * 是否是被排除的 Path
     * @description
     *  eg: "/actuator/health" or "http://127.0.0.1:18200/test/99"
     *  "http://127.0.0.1:18200/test/99".contains("/test/99") => true
     *  "http://127.0.0.1:18200/test/99".contains("/test/")   => true
     * @param currentPath
     * @return
     */
    public static boolean isExcludedPath(String currentPath){
        if(ObjectUtils.isEmpty(currentPath)){
            return false;
        }
        for(int i =0, length = LOG_EXCLUDED_PATHS_LIST.size(); i<length; i++){
            String excludedPath = LOG_EXCLUDED_PATHS_LIST.get(i);
            if(currentPath.contains(excludedPath)){
                return true;
            }
        }
        return false;
    }
}
