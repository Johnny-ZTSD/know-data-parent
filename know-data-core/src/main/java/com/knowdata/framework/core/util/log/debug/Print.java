package com.knowdata.framework.core.util.log.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-date 2022/7/12 16:27
 * @description 控制台(标准输出)打印的调试工具类
 */

public class Print {
    private final static Logger logger = LoggerFactory.getLogger(Print.class);

    public Print() {
        // Do nothing
    }

    public static <T> void print(boolean isOuput, T object) {
        if (isOuput) {
            logger.debug(object.toString());
        }
    }

    public static <T> void printCollection(Collection collection) {
        if (collection != null && collection.size() >= 1) {
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                logger.debug(iterator.next().toString());
            }

        } else {
            logger.debug("collection is null or its size < 1!");
        }
    }

    public static <T> void print(T object) {
        logger.debug(object.toString());
    }

    public static <T> String toString(T object) {
        StringBuilder result = new StringBuilder();
        result.append(object.toString());
        return result.toString();
    }

    public static <T> void println() {
        logger.debug("\n");
    }

    public static <T> void println(T object) {
        logger.debug(object.toString());
    }

    public static <K,V> void println(Map<K, V> map) {
        Set entrySet = map.entrySet();
        entrySet.forEach(entry -> {
            logger.debug(entry.toString());
        });
    }

    public static void print(byte[] array){
        print(array, " ");
    }

    /**
     *
     * @param array 数组
     * @param delimiterCharacter 分割符
     */
    public static void print(byte[] array, String delimiterCharacter){
        if(array == null){
            return;
        }
        boolean delimiterCharEnabled = !StringUtils.isEmpty(delimiterCharacter);
        StringBuilder output = new StringBuilder();
        for(int i=0; i<array.length;i++){
            String byteStr = Byte.toString(array[i]);
            if(delimiterCharEnabled){
                //[comment] if byteArray=[1,104,99]; then `Byte.toString(byteArray[1])` => "104"
                output.append(byteStr);
                if(i+1 != array.length){
                    output.append(delimiterCharacter);
                }
            } else {
                print(byteStr);
            }
        }
        if(delimiterCharEnabled){
            logger.debug(output.toString());
        }
    }

    public static <T> String toString(List<T> list) {
        return toString(list.toArray());
    }

    public static <K, V> void printWithMessage(String message, List<Map<K, V>> list) {
        logger.debug(message);
        list.forEach(map -> {
            Print.print(map);
        });
    }


    public static <T> void print(T[] data) {
        int i = 0;

        for (int size = data.length; i < size; ++i) {
            logger.debug(data[i] + " ");
        }
    }

    public static <T> String toString(T[] data) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (int size = data.length; i < size; ++i) {
            result.append(data[i] + " ");
        }
        return result.toString();
    }

    public static <T> void print(T[][] data) {
        int i = 0;

        for (int rows = data.length; i < rows; ++i) {
            int j = 0;

            for (int var4 = data[i].length; j < rows; ++j) {
                logger.debug(data[i][j] + "\t\t\t\t");
            }
        }
    }

    public static <T> void printWithMessage(String message, T data){
        logger.debug(message);
        Print.print(data);
    }

    public static void print(Map[] data) {
        int i = 0;

        for (int rows = data.length; i < rows; ++i) {
            print(data[i]);
        }
    }

    public static void print(Map data) {
        Set set = data.keySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            Object key = iter.next();
            logger.debug("<" + key + ":" + data.get(key) + ">\t\t\t\t");
        }
    }

    public static String toString(Map data) {
        StringBuilder result = new StringBuilder();
        Set set = data.keySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            Object key = iter.next();
            result.append("<" + key + ":" + data.get(key) + ">\t\t\t\t");
        }
        return result.toString();
    }
}
