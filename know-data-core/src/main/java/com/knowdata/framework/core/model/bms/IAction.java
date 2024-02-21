package com.knowdata.framework.core.model.bms;

import java.io.Serializable;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:24:31
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public interface IAction extends Serializable {
    String code();

    String url();
}