package com.knowdata.framework.idgenerator.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knowdata.framework.idgenerator.entity.WorkerNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/7 20:01
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
@Mapper // 声明为 mybatis 框架
@Repository // 注册到 spring ioc container
public interface WorkerNodeRepository extends BaseMapper<WorkerNode> {
    /**
     * 添加对象
     * @param workerNodeEntity
     * @return
     */
    int addWorkerNode(WorkerNode workerNodeEntity);
}
