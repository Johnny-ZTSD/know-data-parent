package com.knowdata.framework.idgenerator.enums;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/7 18:33
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public enum DataCenterEnum {
    DEFAULT("DEFAULT", 0, "默认的数据中心"),
    HUAWEI_CLOUD_CN_DEV("HUAWEI_CLOUD-CN-DEV", 1, "华为云-中国-开发环境"),
    HUAWEI_CLOUD_CN_TEST("HUAWEI_CLOUD-CN-TEST", 2, "华为云-中国-测试环境"),
    HUAWEI_CLOUD_CN_PT("HUAWEI_CLOUD-CN-PT", 3, "华为云-中国-压测环境"),
    HUAWEI_CLOUD_CN_UAT("HUAWEI_CLOUD-CN-UAT", 4, "华为云-中国-用户接受测试环境"),
    HUAWEI_CLOUD_CN_PROD("HUAWEI_CLOUD-CN-PROD", 5, "华为云-中国-生产环境"),
    HUAWEI_CLOUD_EU_TEST("HUAWEI_CLOUD-EU-TEST", 6, "华为云-欧洲-开发环境"),
    HUAWEI_CLOUD_EU_UAT("HUAWEI_CLOUD-EU-UAT", 7, "华为云-欧洲-用户接受测试环境"),
    HUAWEI_CLOUD_EU_PROD("HUAWEI_CLOUD-EU-PROD", 8, "华为云-欧洲-生产环境"),

    ALIYUN_CLOUD_CN_DEV("ALIYUN_CLOUD-CN-DEV", 9, "阿里云-中国-开发环境"),
    ALIYUN_CLOUD_CN_TEST("ALIYUN_CLOUD-CN-TEST", 10, "阿里云-中国-测试环境"),
    ALIYUN_CLOUD_CN_PT("ALIYUN_CLOUD-CN-PT", 11, "阿里云-中国-压测环境"),
    ALIYUN_CLOUD_CN_UAT("ALIYUN_CLOUD-CN-UAT", 12, "阿里云-中国-用户接受测试环境"),
    ALIYUN_CLOUD_CN_PROD("ALIYUN_CLOUD-CN-PROD", 13, "阿里云-中国-生产环境"),
    ALIYUN_CLOUD_EU_TEST("ALIYUN_CLOUD-EU-TEST", 14, "阿里云-欧洲-开发环境"),
    ALIYUN_CLOUD_EU_UAT("ALIYUN_CLOUD-EU-UAT", 15, "阿里云-欧洲-用户接受测试环境"),
    ALIYUN_CLOUD_EU_PROD("ALIYUN_CLOUD-EU-PROD", 16, "阿里云-欧洲-生产环境"),

    TENCENT_CLOUD_CN_DEV("TENCENT_CLOUD-CN-DEV", 17, "腾讯云-中国-开发环境"),
    TENCENT_CLOUD_CN_TEST("TENCENT_CLOUD-CN-TEST", 18, "腾讯云-中国-测试环境"),
    TENCENT_CLOUD_CN_PT("TENCENT_CLOUD-CN-PT", 19, "腾讯云-中国-压测环境"),
    TENCENT_CLOUD_CN_UAT("TENCENT_CLOUD-CN-UAT", 20, "腾讯云-中国-用户接受测试环境"),
    TENCENT_CLOUD_CN_PROD("TENCENT_CLOUD-CN-PROD", 21, "腾讯云-中国-生产环境"),
    TENCENT_CLOUD_EU_TEST("TENCENT_CLOUD-EU-TEST", 22, "腾讯云-欧洲-开发环境"),
    TENCENT_CLOUD_EU_UAT("TENCENT_CLOUD-EU-UAT", 23, "腾讯云-欧洲-用户接受测试环境"),
    TENCENT_CLOUD_EU_PROD("TENCENT_CLOUD-EU-PROD", 24, "腾讯云-欧洲-生产环境");

    private final String code;
    private final Integer id;
    private final String name;

    public final static String CODE_PARAM = "code";
    public final static String ID_PARAM = "id";
    public final static String NAME_PARAM = "name";

    DataCenterEnum(String code, Integer id, String name){
        this.code = code;
        this.id = id;
        this.name = name;
    }

    public static DataCenterEnum findByCode(String code) {
        for (DataCenterEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static DataCenterEnum findById(Integer id) {
        for (DataCenterEnum type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

    public static DataCenterEnum findByName(String name) {
        for (DataCenterEnum type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = Lists.newArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        for (DataCenterEnum item : DataCenterEnum.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(DataCenterEnum.CODE_PARAM, item.getCode());
            map.put(DataCenterEnum.ID_PARAM, item.getId());
            map.put(DataCenterEnum.NAME_PARAM, item.getName());
            list.add(map);
        }
        return list;
    }
}
