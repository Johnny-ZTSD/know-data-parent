package com.knowdata.framework.siteserver.app.model;


import lombok.Builder;
import lombok.Data;

/**
 * @author Johnny
 * @project love-connect-user-service
 * @create-time 2023/3/30  23:39:11
 * @description Swagger 自定义配置
 * @reference-doc
 *  [1] SpringBoot项目配置Swagger - CSDN - https://blog.csdn.net/adminwxc/article/details/127744816
 */

@Data
@Builder
public class SwaggerProperties {

    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;

    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;

    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
