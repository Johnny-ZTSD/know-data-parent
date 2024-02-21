package com.knowdata.framework.core.model.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/26  19:48:46
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */
public class BaseObject implements Serializable {
    /**
     * @sample toString sample : "BasePage[currentPage=1,pageSize=78]"
     **/
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
