package com.knowdata.framework.core.util.lang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowdata.framework.core.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/6/21 18:54
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public class ObjectUtils {
    private final static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    public static <T> boolean isInstance(T bean, Class clazz){
        return clazz.isInstance(bean);
    }

    /**
     *
     * @param bytes
     * @return
     * @reference-doc
     *  [1] java中把对象转化为byte数组的方法 - 百度文库 - https://wenku.baidu.com/view/08c8841480c4bb4cf7ec4afe04a1b0717fd5b3df.html
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object object = null;
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        ObjectInputStream bis = null;
        try {
            bis = new ObjectInputStream(bai);
        } catch (IOException e) {
            logger.error("Fail to convert the bytes as Object! (bytes.length : {}, errorMessage : {})", bytes.length, e.getMessage());
            throw new RuntimeException(e);
        }

        object = bis.readObject();

        bai.close();
        bis.close();
        return object;
    }

    public static byte[] toBytes(Object object) throws IOException {
        //Object object = null;
        byte[] bytes = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream bos = new ObjectOutputStream(bao);

        bos.writeObject(object);

        bytes = bao.toByteArray();

        bao.close();
        bos.close();
        return bytes;
    }

    public static String toJson(Object object) {
        return toJson(object, new ObjectMapper());
    }

    public static String toJson(Object object, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Fail to convert as json string!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Determine whether the given object is empty.
     * <p>This method supports the following object types.
     * <ul>
     * <li>{@code Optional}: considered empty if {@link Optional#empty()}</li>
     * <li>{@code Array}: considered empty if its length is zero</li>
     * <li>{@link CharSequence}: considered empty if its length is zero</li>
     * <li>{@link Collection}: delegates to {@link Collection#isEmpty()}</li>
     * <li>{@link Map}: delegates to {@link Map#isEmpty()}</li>
     * </ul>
     * <p>If the given object is non-null and not one of the aforementioned
     * supported types, this method returns {@code false}.
     * @param obj the object to check
     * @return {@code true} if the object is {@code null} or <em>empty</em>
     * @since spring-framework 4.2
     * @see Optional#isPresent()
     * @see ObjectUtils#isEmpty(Object[])
     * @see //StringUtils#hasLength(CharSequence)
     * @see //StringUtils#isEmpty(Object)
     * @see //CollectionUtils#isEmpty(java.util.Collection)
     * @see //CollectionUtils#isEmpty(java.util.Map)
     * @reference-doc
     *  { @link org.springframework.util.ObjectUtils#isEmpty(Object[]) }
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional<?>) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }

    /**
     * Determine whether the given array is empty:
     * i.e. {@code null} or of zero length.
     * @param array the array to check
     * @see #isEmpty(Object)
     */
    public static boolean isEmpty(@Nullable Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断是否为整数
     * @reference-doc
     *  [1] Java判断字符串是否为数字的几个方法 - oh100.com - http://www.oh100.com/kaoshi/java/565460.html
     * @param str
     * @return
     */
    public static Boolean isIntegerNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为数字
     * @reference-doc
     *  [1] Java判断字符串是否为数字的几个方法 - oh100.com - http://www.oh100.com/kaoshi/java/565460.html
     * @param str
     * @return
     */
    public static Boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9\\.]*");
        return pattern.matcher(str).matches();
    }

    /**
     * bool convert to int
     * @reference-doc
     *  [1] 如何在 Java 中将布尔值转换为 Int - 火焰兔 - https://www.zadmei.com/rhzjzjbe.html
     * @param boolValue
     * @return
     */
    public static int toInt(boolean boolValue){
        return boolValue?1:0;
    }
}
