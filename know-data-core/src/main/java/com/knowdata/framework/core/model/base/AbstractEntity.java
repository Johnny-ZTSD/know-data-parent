package com.knowdata.framework.core.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowdata.framework.core.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Table;

//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  01:58:27
 * @description 抽象的数据库实体基类
 *  1. 使用@Table、@Id、@Column的注解来完成对特殊表名、主键、特殊列名的标记，来增加更多适用性; [行业最佳实践]
 *  2. 当然，也可以使用 mybatis-plus 的 com.baomidou.mybatisplus.annotation.TableField/... 注解
 * @reference-doc
 *  [1] mybatis-plus 直接查询的数据对对象带嵌套的对象  - 博客园 - https://www.cnblogs.com/PythonOrg/p/17053250.html
 *  [2] MybatisPlus 主键策略（type=IdType.ASSIGN_ID等详解） - 博客园 - https://www.cnblogs.com/mark5/p/14268122.html
 * @gpt-prompt
 */

@Data
//@Table(name = "tb_xxxx")
//@TableName("tb_xxxx")
public class AbstractEntity implements Serializable {

    //@ApiModelProperty("用户Id")
    //@JsonProperty
    //@TableId(type = IdType.INPUT) //INPUT : 插入前自行设置主键 / AUTO : 数据库ID自增 / ASSIGN_ID : 雪花算法 / ASSIGN_UUID : 排除中划线的UUID
    //private Long id;

    @ApiModelProperty("创建人")
    @JsonIgnore
    //@CreatedBy
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonIgnore
    //@Column(
    //        columnDefinition = "datetime(3)",
    //        nullable = false
    //)
    private LocalDateTime createAt = LocalDateTime.now(Constants.ZONE_ID);

    @ApiModelProperty("修改人")
    @JsonIgnore
    //@LastModifiedBy
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonIgnore
    //@LastModifiedDate
    private LocalDateTime updateAt = LocalDateTime.now(Constants.ZONE_ID);

    /**
     * 删除状态
     * 2:待删除(申请删除中); 1:已删除;  0:未删除
     */
    @TableField(value = "is_delete", exist = true)
    private Integer deleted;

    public AbstractEntity() {

    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt == null ? null : this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateAt() {
        return this.updateAt == null ? null : (LocalDateTime)this.updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
//        return "BaseEntity{" +
//                "createBy='" + createBy + '\'' +
//                ", createAt=" + createAt +
//                ", updateBy='" + updateBy + '\'' +
//                ", updateAt=" + updateAt +
//                '}';
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
