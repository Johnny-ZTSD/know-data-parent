package com.knowdata.framework.core.model.bms;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:22:48
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

@ApiModel("行政区划级别/区划级别")
public enum AreaLevelEnum {
    NATION("NATION", "国家", 1)
    , PROVINCE("PROVINCE", "省份", 2)
    , STATE("STATE", "州市", 3)
    , COUNTY("COUNTY", "区县", 4)
    , STREET("STREET", "乡镇", 5)
    , VILLAGE("VILLAGE", "社区/村庄", 6)
    ;

    private final String code;
    private final String name;
    private final Integer level;

    public final static String CODE_PARAM = "code";
    public final static String NAME_PARAM = "name";
    public final static String LEVEL_PARAM = "level";

    AreaLevelEnum(String code, String name, Integer level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public static AreaLevelEnum findByCode(String code) {
        for (AreaLevelEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static AreaLevelEnum findByName(String name) {
        for (AreaLevelEnum type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static AreaLevelEnum findByLevel(Integer level) {
        for (AreaLevelEnum type : values()) {
            if (type.getLevel().equals(level)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getLevel() {
        return this.level;
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList();
        for (AreaLevelEnum item : AreaLevelEnum.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(AreaLevelEnum.CODE_PARAM, item.getCode());
            map.put(AreaLevelEnum.NAME_PARAM, item.getName());
            map.put(AreaLevelEnum.LEVEL_PARAM, String.valueOf(item.getLevel()));
            list.add(map);
        }
        return list;
    }
}
