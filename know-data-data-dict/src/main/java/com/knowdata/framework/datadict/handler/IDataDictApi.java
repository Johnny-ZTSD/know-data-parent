package com.knowdata.framework.datadict.handler;

//import cn.xxx.bd.datasource.api.Constants;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;

import com.knowdata.framework.core.model.dto.BasePage;
import com.knowdata.framework.core.model.dto.XResponse;
import com.knowdata.framework.datadict.entity.DataDict;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

/**
 * @author johnny-zen
 * @version v1.0
 * @project bdp_common_datasource_service
 * @create-time 2023/9/8 9:02
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public interface IDataDictApi {
    /**
     * 获取所有的数据字典码
     * @param page
     * @return
     */
//    @PostMapping( value = {Constants.DataDict.GET_DATA_DICT_CODE_LIST_API })
    public XResponse<List<String>> listAllDataDictCode(
        @RequestBody BasePage page
    );

//    @PostMapping( value = {Constants.DataDict.GET_DATA_DICT_ITEMS_API })
    public XResponse<DataDict> getDataDictItems(String dataDictCode);
//
//    @PostMapping( value = {Constants.GET_DATA_DICT_ITEMS_API })
    //public XResponse<List<DataDictVO>> getDataDictItems (
        //@RequestParam("dataDictCode") String dataDictCode // 数据字典Code , 对应 field_enum_type
    //);
}
