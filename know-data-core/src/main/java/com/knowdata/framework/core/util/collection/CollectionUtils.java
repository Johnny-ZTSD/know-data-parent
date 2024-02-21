package com.knowdata.framework.core.util.collection;

import java.util.*;

import com.alibaba.fastjson2.JSON;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2022/10/3 9:20
 * @description ...
 */
public class CollectionUtils {
    /**
     * map 转 2维数组
     * @return KVArrays
     *  KVArrays.keyArray : keySetArray
     *  KVArrays.valueArray : valueSetArray
     */
    public static <K, V> KVArrays mapToKVArrays(Map<K, V> map){
        KVArrays kvArrays = new KVArrays();
        LinkedList<K> keyArrayList = new LinkedList();
        LinkedList<V> valueArrayList = new LinkedList();
        map.entrySet().forEach(entry -> {
            keyArrayList.add(entry.getKey());
            valueArrayList.add(entry.getValue());
        });

        kvArrays = new KVArrays(keyArrayList.toArray(), valueArrayList.toArray());
        return kvArrays;
    }

    /**
     * 清除字符串数组中每个元素的首尾空格符
     * @param strArray
     * @return
     */
    public static String [] clearEmptyChar(String [] strArray){
        if(strArray == null){
            return null;
        }
        String [] newArray = new String [strArray.length];
        for(int i=0;i<strArray.length;i++){
            newArray[i] = strArray[i].trim();
        }
        return newArray;
    }

    /**
     * map 转 对象
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     * @reference-doc
     *  [1] Java Map转换实体类对象简单实现 - 博客园 - https://www.cnblogs.com/shisanye/p/13938282.html
     *  [2] java map转对象 - CSDN - https://blog.csdn.net/m0_67402774/article/details/126387146
     */
    public  static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if(map == null){
            return null;
        }
        // method 1: 不可靠(存在局限性)
        //org.apache.commons.beanutils.BeanUtils.populate(beanClass.newInstance(), map);
        // method 2: fastjson2
        return JSON.parseObject(JSON.toJSONString(map), beanClass);
    }

    public static Map objectToMap(Object obj) {
        if(obj == null){
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    public static List enumerationToList(Enumeration enumeration){
        List list = new LinkedList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration);
        }
        return list;
    }

    public static <T extends Map> boolean isEmpty(T bean){
        if(bean == null){
            return true;
        }
        return bean.isEmpty();
    }

    public static <T extends Collection> boolean isEmpty(T bean){
        if(bean == null){
            return true;
        }
        return bean.isEmpty();
    }

    public static <T extends Map> boolean isNotEmpty(T bean){
        return !isEmpty(bean);
    }

    public static <T extends Collection> boolean isNotEmpty(T bean){
        return !isEmpty(bean);
    }
}
