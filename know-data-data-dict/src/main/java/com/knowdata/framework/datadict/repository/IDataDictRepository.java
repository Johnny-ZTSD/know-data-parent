package com.knowdata.framework.datadict.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knowdata.framework.datadict.entity.DataDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/7 20:55
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
@Repository //[spring] 注册到 spring ioc 中为 bean
@Mapper //[mybatis] 便于加载 mapper.xml
public interface IDataDictRepository extends BaseMapper<DataDict> {

}
