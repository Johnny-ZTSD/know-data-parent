package com.knowdata.framework.core.model.bms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:25:28
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */
@Data
@AllArgsConstructor
@ApiModel("角色操作权限表(tb_role_action_permission)")
public class Action {
    @ApiModelProperty("角色权限ID")
    @JsonProperty
    private Long id;

    @ApiModelProperty("父级功能ID")
    @JsonProperty
    private Long parentActionId;

    @ApiModelProperty("功能权限全路径(冗余字段，多层级权限间以,分隔)")
    @JsonProperty
    private String rootPath;

    @ApiModelProperty("功能代码(形如：GET_USER_ACCOUNT_INFO(获取用户账号信息))")
    @JsonProperty
    private String actionCode;

    @ApiModelProperty("功能名称")
    @JsonProperty
    private String actionName;

    @ApiModelProperty("功能对应的接口地址(形如：/user-service/user/getUserAccountInfo)")
    @JsonProperty
    private String apiPath;

    @ApiModelProperty("请求方法(形如：POST,DATA_DICT_TYPE=REQUEST_METHOD)")
    @JsonProperty
    private String requestMethod;

    @ApiModelProperty("重要度权重(形如：0(普通操作，每个用户均拥有的权限。重要度权重，分数越高说明越重要)")
    @JsonProperty
    private String importanceWeight;

    @ApiModelProperty("拥有操作权限的用户角色类型列表(多个角色间以,分隔)，形如: REGISTER_USER,AUTH_USER, ADMIN_USER")
    @JsonProperty
    private String grantedRoles;

    @Override
    public String toString() {
        return "RolePermissionPO{" +
                "id=" + id +
                ", parentActionId=" + parentActionId +
                ", rootPath='" + rootPath + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", actionName='" + actionName + '\'' +
                ", apiPath='" + apiPath + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", importanceWeight='" + importanceWeight + '\'' +
                ", grantedRoles='" + grantedRoles + '\'' +
                '}';
    }
}
