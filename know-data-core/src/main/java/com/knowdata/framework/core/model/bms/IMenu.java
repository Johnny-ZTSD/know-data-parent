package com.knowdata.framework.core.model.bms;

import java.io.Serializable;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  02:24:16
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public interface IMenu extends Serializable {
    String code();

    String url();
}