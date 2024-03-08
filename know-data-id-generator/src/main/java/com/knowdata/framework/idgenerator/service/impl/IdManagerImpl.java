package com.knowdata.framework.idgenerator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.knowdata.framework.core.util.net.NetworkUtils;
import com.knowdata.framework.idgenerator.entity.WorkerNode;
import com.knowdata.framework.idgenerator.enums.DataCenterEnum;
import com.knowdata.framework.idgenerator.enums.RunningStatusEnum;
import com.knowdata.framework.idgenerator.repository.WorkerNodeRepository;
import com.knowdata.framework.idgenerator.service.IdManager;
import com.knowdata.framework.idgenerator.service.WorkerNodeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/5 20:34
 * @description ...
 * @refrence-doc
 *  [1] 雪花算法(snowflake)容器化部署支持动态增加节点 - CSDN - https://blog.csdn.net/u012410733/article/details/121882691
 * @gpt-promt
 */
public class IdManagerImpl implements IdManager {
    private final static Logger logger = LoggerFactory.getLogger(IdManagerImpl.class);

    private WorkerNodeRepository workerNodeRepository;

    private WorkerNodeIdGenerator workerNodeIdGenerator;

    private Long workerId;

    private DataCenterEnum dataCenter;

    public IdManagerImpl(WorkerNodeRepository workerNodeRepository, DataCenterEnum dataCenterEnum){
        this.workerNodeRepository = workerNodeRepository;
        this.dataCenter = dataCenterEnum;
        init();
    }

    public void init(){
        //step1 清理死掉的应用实例
        //step1.1 查询配置表中状态为存活(或未检查的)的节点
        QueryWrapper<WorkerNode> queryWrapper = new QueryWrapper();
        queryWrapper.eq(WorkerNode.RUNNING_STATUS_FIELD_PARAM, RunningStatusEnum.RUNNING.getCode());
        List<WorkerNode> runningWorkerNodeList = workerNodeRepository.selectList(queryWrapper);

        //step1.2 逐一检测，并更新实质上已死掉的应用实例的运行状态
        if(ObjectUtils.isEmpty(runningWorkerNodeList)){
            runningWorkerNodeList.stream().forEach(runningWorkerNode -> {
                if( ! isRealRunningWorkerNode( runningWorkerNode ) ){
                    runningWorkerNode.setRunningStatus(RunningStatusEnum.STOPPED.getCode());
                    workerNodeRepository.updateById(runningWorkerNode);
                }
            });
        }

        //step2 基于当前应用实例列表，获得 workerId , dataCenterId
        //TODO

        //step3 基于 workerId , dataCenterId 创建 IdGenerator
        logger.info("snowflake # IdManager | worker node id is {}, data center id is{}", this.workerId, dataCenter);
        workerNodeIdGenerator = new WorkerNodeIdGenerator(this.workerId, dataCenter.getId());
    }

    /** 当前节点实质上是否处于运行状态 **/
    public static boolean isRealRunningWorkerNode(WorkerNode workerNode) {
        boolean pingResult = false;
        try {
            pingResult = NetworkUtils.hostAndPortTest(workerNode.getHostName(), workerNode.getPort(), 3000);
        } catch (Exception exception) {
            logger.error("Fail to ping the worker node's hostname! worker node : {}, exception : {}", workerNode, exception);
        }
        return pingResult;
    }

    @Override
    public String getId() {
        return String.valueOf( workerNodeIdGenerator.nextId() );
    }
}
