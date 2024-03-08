package com.knowdata.framework.idgenerator.enums;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/7 20:05
 * @description 服务实例的运行状态
 *  RUNNING(running)/运行中或未检查
 *  STOPPED/已停止运行
 * @refrence-doc
 * @gpt-promt
 */
public enum RunningStatusEnum {
    RUNNING("RUNNING", "运行中或未检查"),
    STOPPED("STOPPED", "已停止运行");

    private final String code;
    private final String name;

    public final static String CODE_PARAM = "code";
    public final static String NAME_PARAM = "name";

    RunningStatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static RunningStatusEnum findByCode(String code) {
        for (RunningStatusEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static RunningStatusEnum findByName(String name) {
        for (RunningStatusEnum type : values()) {
            if (type.getName().equals(name)) {
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

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> list = Lists.newArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        for (RunningStatusEnum item : RunningStatusEnum.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(RunningStatusEnum.CODE_PARAM, item.getCode());
            map.put(RunningStatusEnum.NAME_PARAM, item.getName());
            list.add(map);
        }
        return list;
    }
}
