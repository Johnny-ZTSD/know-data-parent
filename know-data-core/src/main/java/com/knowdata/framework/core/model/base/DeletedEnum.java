package com.knowdata.framework.core.model.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:18:14
 * @description 删除状态的枚举类
 * @reference-doc
 * @gpt-prompt
 */

public enum DeletedEnum {
    NOT_DELETED(0, "未被删除")
    , HAS_DELETED(1, "已被删除")
    , DELETING(2, "删除中");

    public final static String CODE_PARAM = "code";
    public final static String NAME_PARAM = "name";

    private Integer code;
    private String name;


    DeletedEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static DeletedEnum findByCode(String code) {
        for (DeletedEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static DeletedEnum findByName(String name) {
        for (DeletedEnum type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = new ArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        for (DeletedEnum item : DeletedEnum.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(DeletedEnum.CODE_PARAM, item.getCode());
            map.put(DeletedEnum.NAME_PARAM, item.getName());
            list.add(map);
        }
        return list;
    }
}
