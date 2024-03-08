-- kd_worker_node definition

CREATE TABLE `kd_worker_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID/auto increment id',
  `service_name` varchar(128) NOT NULL COMMENT '应用服务名称/service name. eg: bdp-data-service',
  `service_instance` varchar(128) NOT NULL COMMENT '服务实例名称/service instance. eg: bdp-data-service-parent-backend-service-86c6894d44-m7ljf',
  `host_name` varchar(64) NOT NULL COMMENT '主机名/host name. eg: bdp-data-service-parent-backend-service-86c6894d44-m7ljf',
  `ip` varchar(64) NOT NULL COMMENT '网络地址/ip address, eg: 123.34.67.23',
  `port` int(10) unsigned NOT NULL COMMENT '服务实例的主端口/port. eg: 8080',
  `node_type` int(11) NOT NULL COMMENT '节点类型/node type: ACTUAL(实际物理主机) or CONTAINER(虚拟化容器)',
  `launch_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '应用服务的启动时间/launch time of service instance',
  `running_status` varchar(64) NOT NULL COMMENT '服务实例运行状态/running status of service instance: RUNNING(running)/运行中或未检查; STOPPED/已停止运行',
  `worker_id` int(11) NOT NULL COMMENT '工作机器ID/worker node server id. eg: 0,3,...,31',
  `data_center_id` int(11) NOT NULL COMMENT '数据中心ID/data center id. eg: 0,3,...,31',
  `data_center_code` varchar(128) NOT NULL COMMENT '数据中心代码/data center code. eg: HUAWEI_CLOUD-CN-TEST',
  
  -- `create_at` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间/created time',
  `create_at` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间/created time',
  `create_by` varchar(15) NOT NULL DEFAULT '-1' COMMENT '创建人/creater. default: -1(super administrator)',
  -- `update_at` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间/modified time',
  `update_at` timestamp DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间/modified time',
  `update_by` varchar(15) NOT NULL DEFAULT '-1' COMMENT '更新人/updater. default: -1(super administrator)',

  `is_delete` int(11) NOT NULL COMMENT '删除标识/delete flag:0-false/undelete(default value)/未删除(默认值),1-true/deleted/已删除',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Worker Node for SNOWFLAKE(雪花算法) | 1)1个微服务实例仅能占用1个workerId;2)有效的WorkerId仅能被1个微服务实例占用';