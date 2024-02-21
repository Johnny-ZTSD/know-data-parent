package com.knowdata.framework.datadict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.knowdata.framework.core.model.base.AbstractEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

/**
 * @author johnny-zen
 * @version v1.0
 * @project bdp_common_datasource_service
 * @create-time 2023/9/7 13:12
 * @description ...
 * @refrence-doc
 *  [1] 数据字典 - 博客园 - https://www.cnblogs.com/vvlj/p/12750590.html
 *  [2] 微服务架构-数据字典设计 - Zhihu - https://zhuanlan.zhihu.com/p/365156862
 *  [3] MyBatisPlus Annotation#TableField - github - https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/TableField.java
 * @gpt-promt
 */
@Data
@AllArgsConstructor
@Getter
@Setter
@ApiModel("数据字典表")
@TableName(DataDict.TABLE_NAME_PARAM)
public class DataDict extends AbstractEntity {
    public final static String TABLE_NAME_PARAM = "ds_data_dict";

    /**
     * context 字段的默认值
     */
    private final static String DEFAULT_CONTEXT_FIELD = "default-context";

    @TableField(value = "context", jdbcType = JdbcType.VARCHAR)
    private String context;

    /** 数据字典 **/

    /**
     * 数据字典代号
     * @description <p>区分好【数据字典】与【数据项】</p>
     */
    @TableField(value = "data_dict_code", jdbcType = JdbcType.VARCHAR)
    private String dataDictCode;

    /**
     * 数据字典描述
     *
     */
    @TableField(value = "data_dict_describe", jdbcType = JdbcType.VARCHAR)
    private String dataDictDescribe;

    /** ---- 数据项 ---- **/

    /**
     * 数据项代码
     */
    @TableField(value = "data_item_code", jdbcType = JdbcType.VARCHAR)
    private String dataItemCode;

    /**
     * 数据项名称
     */
    @TableField(value = "data_item_name", jdbcType = JdbcType.VARCHAR)
    private String dataItemName;

    /**
     * 数据项别名
     */
    @TableField(value = "data_item_alias", jdbcType = JdbcType.VARCHAR)
    private String dataItemAlias;

    /**
     * 数据项描述
     */
    @TableField(value = "data_item_describe", jdbcType = JdbcType.VARCHAR)
    private String dataItemDescribe;

    /**
     * 数据项序号
     */
    @TableField(value = "data_item_order", jdbcType = JdbcType.VARCHAR)
    private String dataItemOrder;

    @Override
    public String toString() {
        return "DataDict{" +
                "context='" + context + '\'' +
                ", dataDictCode='" + dataDictCode + '\'' +
                ", dataDictDescribe='" + dataDictDescribe + '\'' +
                ", dataItemCode='" + dataItemCode + '\'' +
                ", dataItemName='" + dataItemName + '\'' +
                ", dataItemAlias='" + dataItemAlias + '\'' +
                ", dataItemDescribe='" + dataItemDescribe + '\'' +
                ", dataItemOrder='" + dataItemOrder + '\'' +
                '}';
    }
}
