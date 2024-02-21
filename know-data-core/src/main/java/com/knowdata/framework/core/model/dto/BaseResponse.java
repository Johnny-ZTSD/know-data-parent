package com.knowdata.framework.core.model.dto;

import com.knowdata.framework.core.model.base.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/2/21  01:31:52
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */
@ApiModel(value = "基础响应对象", description = "基础响应 Bean")
@Data
@NoArgsConstructor
public class BaseResponse<T> extends BaseObject {
    @ApiModelProperty(
            value = "基础响应内容(含义：本页响应的数据集)",
            example = "",
            hidden = false
    )
    // T : 一般为集合类的泛型，例如： Map<String, Object> / List<Map<String, Object>> / List<Object>
    private List<T> records;

    public BaseResponse(T record){
        List<T> records = new ArrayList<>();
        records.add(record);
        this.records = records;
    }
}
