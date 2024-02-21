package com.knowdata.framework.core.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author: johnnyzen
 * @date: 2022/8/13  13:05:58
 * @description: ...
 */

public class ClassLoaderUtils {
    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtils.class);

    /**
     * 获取 SystemClassLoader 的 classpath (绝对URI路径)
     * @return
     *  eg: "/E:/source_code/know-data-parent/bdp_data_service/bdp-data-service-common/target/test-classes/"
     */
    public static String getClassPath(){
        URL resource = ClassLoader.getSystemResource("/");
        if(ObjectUtils.isEmpty(resource)){
            String errorMessage = "Fail to get classpath when execute `ClassLoader.getSystemResource(...)`";
            logger.warn(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return resource.getPath();
//        return getClassPath(Object.class.getClassLoader());// 方式2
    }

    /**
     * 获取指定 classLoader 下的 classpath (绝对URI路径)
     * @param classLoader
     * @return
     *  eg:
     *    "/E:/source_code/know-data-parent/bdp_data_service/bdp-data-service-common/target/test-classes/"
     */
    public static String getClassPath(ClassLoader classLoader){
        return getClassPath(classLoader, "/");
    }

    public static String getClassPath(ClassLoader classLoader, String relativePath){
        return classLoader.getResource(relativePath).getPath();
    }

    /**
     * 获取指定 Class 下的 classpath (绝对URI路径)
     * @reference-doc
     *  [1] JAVA获取CLASSPATH路径 - 博客园 - https://www.cnblogs.com/tk55/p/6064167.html
     * @param clazz
     * @return
     *  eg:
     *      If clazzObj = new com.test.Clazz(), then:
     *          1) clazz = clazzObj.getClass();
     *          2) relativePath(相对路径) :
     *              a) relativePath = "",  then: clazzObj.getClass().getResource("") = 当前类clazz的类文件的URI目录 = /D:/xxx/target/classes/com/test/
     *              b) relativePath = "/", then: clazzObj.getClass().getResource("") = 当前类clazz的类文件的URI目录 = /D:/xxx/target/classes/
     */
    public static String getClassPath(Class clazz, String relativePath){
        return clazz.getResource(relativePath).getPath();
    }

    public static String getClassPath(Object object, String relativePath){
        return object.getClass().getResource(relativePath).getPath();
    }

    /**
     * @description
     *  Thread.currentThread().getContextClassLoader ().getResource("") :=> return "/D:/xxx/target/classes/"
     * @param threadObject
     * @param relativePath
     * @return
     */
    public static String getClassPath(Thread threadObject, String relativePath){
        return threadObject.getContextClassLoader().getResource(relativePath).getPath();
    }

    /**
     * 获取 ServletContext 的 classLoader
     * @description 默认从 WebAPP 根目录下取资源，Tomcat下 path 是否以’/'开头无所谓，当然这和具体的 Web Server 容器实现有关。
     * @param servletContext
     * @return
     */
    public static ClassLoader getClassLoader(ServletContext servletContext){
        return servletContext.getClassLoader();
    }

    /**
     * 获取 线程 对应的 classLoader
     * @sample Thread.currentThread().getContextClassLoader(); // 使用当前线程的ClassLoader
     * @param thread
     * @return
     */
    public static ClassLoader getClassLoader(Thread thread){
        return thread.getContextClassLoader();
    }

    /**
     * 获取当前实例对象 对应的 classLoader
     * @sample this.getClass.getClassLoader();  // 使用当前类的ClassLoader
     * @param object
     * @return
     */
    public static ClassLoader getClassLoader(Object object){
        return object.getClass().getClassLoader();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (null == classLoader) {
                classLoader = getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取 系统级 ClassLoader
     * @description
     *  System ClassLoader与 根 ClassLoader 并不一样。 JVM 下 System ClassLoader 通常为 App ClassLoader
     *  ClassLoader.getSystemClassLoader() // 使用系统 ClassLoader (系统的入口点所使用的ClassLoader)
     * @return
     */
    public static ClassLoader getSystemClassLoader() {
        return System.getSecurityManager() == null ? ClassLoader.getSystemClassLoader() : (ClassLoader) AccessController.doPrivileged((PrivilegedAction<ClassLoader>) ClassLoader::getSystemClassLoader);
    }


    public static ClassLoader getContextClassLoader() {
        return System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : (ClassLoader) AccessController.doPrivileged((PrivilegedAction<ClassLoader>) () -> {
            return Thread.currentThread().getContextClassLoader();
        });
    }
}
