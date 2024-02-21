package com.knowdata.framework.core.security;

import com.alibaba.fastjson2.filter.ValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/7/11 15:30
 * @description 数据安全 - 业务实体对象脱敏的值过滤器
 * @refrence-doc
 * @gpt-promt
 */
public class EntityObjectDesensitizationValueFilter implements ValueFilter {
    private final static Logger logger = LoggerFactory.getLogger(EntityObjectDesensitizationValueFilter.class);

    /**
     * 脱敏规则
     */
    public final static String DESENSITIZATION_REGEXP = "@#.*?#@";

    /** 用于 String.format(DESENSITIZATION_TEMPLATE, idCard) 的脱敏格式模板 **/
    public final static String DESENSITIZATION_TEMPLATE = "@#%s#@";

    public static String IDCARD_PARAM = "idCard";

    public static String IDCARD_LIST_PARAM = "idCardList";

    /**
     * 对象脱敏 | 基于 业务中台的 Layout : @#%s#@
     * @param object
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    public Object apply(Object object, String propertyName, Object propertyValue) {
        logger.debug("object: {}, propertyName:{}, propertyValue: {}", object, propertyName, propertyValue);
        Object newPropertyValue = null; // 只要字段名中包含 VIN_PARAM ，则:值输出为 returnValue;
        if (propertyName.equals(IDCARD_PARAM)) {
            if(propertyValue instanceof String){
                String vin = (String) propertyValue;
                return String.format(DESENSITIZATION_TEMPLATE, vin);//  返回修改后的属性值
            } else {
                //Do Nothing
            }
        } else if(propertyName.equals(IDCARD_LIST_PARAM)) {
            if( (!ObjectUtils.isEmpty(propertyValue)) && (propertyValue instanceof List) ){
                List idCardList = (List) propertyValue;
                Object firstElement = idCardList.get(0);
                if(firstElement instanceof String){// vinList's data structure is List<String>
                    List<String> newIdCardList = new ArrayList<>();
                    idCardList.stream().forEach( idCard -> {
                        newIdCardList.add(String.format(DESENSITIZATION_TEMPLATE, idCard));
                    });
                    newPropertyValue = newIdCardList;
                    return newPropertyValue;
                }  else {
                   //Do Nothing
                }
            } else {
                //Do Nothing
            }
        }
        return propertyValue;
    }

    /**
     * 文本脱敏 (脱敏策略应用于文本) | 基于 base64
     * @return
     */
    public static String textDesensitization(String content){
        if(ObjectUtils.isEmpty(content)){
            return null;
        } else {
            if(content.contains(IDCARD_PARAM) || content.contains(IDCARD_LIST_PARAM)){
                return "#ENTITY_OBJECT_DESENSITIZATION_START#" + Base64Utils.encodeToString(content.getBytes()) + "#ENTITY_OBJECT_DESENSITIZATION_END#";
            }
        }
        return content;
    }
}
