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

    /**
     * 求两集合(set1-set2)的差集
     * @description
     *  CASE 1 : set1 = { 1,2,3 } ; set2 = { 2 , 4 , 5 } => result : { 1 , 3 }
     *  CASE 2 : set1 = { 2 , 4 , 5 } ; set2 = { 1,2,3 } => result : { 4,  5 }
     **/
    public static <T> Set<T> subtract(Set<T> setA, Set<T> setB) {
        Set<T> resSet = new HashSet<>(setA);
        resSet.removeAll(setB);
        return resSet;
    }


    /**
     * 取交集（取两个集合中都存在的元素）
     * @return
     */
    public static <T> Set<T> intersection(Set<T> setA, Set<T> setB){
        Set<T> resSet = new HashSet<>(setA);
        /**
         * retainAll(collectiion) : 用于保留集合中属于另一集合的元素，同时移除不属于另一个集合的元素
         *  注: 本方法会修改当前集合，使其包含与指定集合相同的元素
         */
        resSet.retainAll(setB);
        return resSet;
    }

    /**
     * 取并集
     * @return
     */
    public static <T> Set<T> union(Set<T> setA, Set<T> setB){
        Set<T> resSet = new HashSet<>();
        resSet.addAll(setA);
        resSet.addAll(setB);
        return resSet;
    }
}
