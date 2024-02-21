package com.knowdata.framework.core.model.bms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.knowdata.framework.core.model.base.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:20:55
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

@ApiModel
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
public abstract class TreeNode<T extends TreeNode> extends AbstractEntity implements Serializable {
    @ApiModelProperty("节点id")
    private String id;

    @ApiModelProperty("父节点id")
    private String pid;

    @ApiModelProperty("子节点列表")
    private List<T> children = new ArrayList();

    @ApiModelProperty(
            value = "是否叶子节点",
            example = "0-是;1-否"
    )
    @JsonProperty
    private Integer isLeaf;

    @ApiModelProperty("节点路径")
    @JsonProperty
    private String path;

    public TreeNode() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return this.children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public Integer getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}