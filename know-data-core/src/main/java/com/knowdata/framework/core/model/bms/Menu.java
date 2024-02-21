package com.knowdata.framework.core.model.bms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knowdata.framework.core.model.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:19:31
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public class Menu extends AbstractEntity implements IMenu, Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单Id")
    @JsonProperty
    private String id;

    @ApiModelProperty("菜单名称")
    @JsonProperty
    private String name;

    @ApiModelProperty("菜单地址")
    @JsonProperty
    private String url;

    @ApiModelProperty("上级菜单")
    @JsonProperty
    private String parentId;

    @ApiModelProperty("菜单图标")
    @JsonProperty
    private String icon;

    @ApiModelProperty("菜单展示类型")
    @JsonProperty
    private int showType;

    @ApiModelProperty("菜单编码")
    @JsonProperty
    private String code;

    @ApiModelProperty("序号")
    @JsonProperty
    private Integer sortKey;

    @ApiModelProperty("是否可见")
    @JsonProperty
    private Integer isEnable = 1;

    @ApiModelProperty("子菜单")
    @JsonProperty
    private List<Menu> children;

    public Menu() {
    }

    public Menu(String code, String url) {
        this.code = code;
        this.url = url;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String url() {
        return this.url;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSortKey() {
        return this.sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return this.children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getShowType() {
        return this.showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
