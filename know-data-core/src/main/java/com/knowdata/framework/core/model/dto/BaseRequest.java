package com.knowdata.framework.core.model.dto;

import com.knowdata.framework.core.model.base.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/2/21  01:28:45
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */
@ApiModel(value = "基础请求对象", description = "基础请求 Bean")
@Data
@NoArgsConstructor
public class BaseRequest<T> extends BaseObject {
    @ApiModelProperty(
            value = "基础请求参数",
            example = "",
            hidden = false
    )
    private T params;
}
