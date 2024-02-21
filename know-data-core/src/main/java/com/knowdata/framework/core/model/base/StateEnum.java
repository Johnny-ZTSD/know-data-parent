package com.knowdata.framework.core.model.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:20:12
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public enum StateEnum {
    ENABLED(1, "启用中")
    , DISABLED(0, "已停用");

    public final static String CODE_PARAM = "code";
    public final static String NAME_PARAM = "name";

    private Integer code;
    private String name;


    StateEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static StateEnum findByCode(String code) {
        for (StateEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static StateEnum findByName(String name) {
        for (StateEnum type : values()) {
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
        for (StateEnum item : StateEnum.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(StateEnum.CODE_PARAM, item.getCode());
            map.put(StateEnum.NAME_PARAM, item.getName());
            list.add(map);
        }
        return list;
    }
}