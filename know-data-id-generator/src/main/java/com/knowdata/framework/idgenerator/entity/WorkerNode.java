package com.knowdata.framework.idgenerator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.knowdata.framework.core.model.base.AbstractEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

import java.sql.Timestamp;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/9/7 19:35
 * @description
 *  [1] Timestamp => LocalDateTime
 *      Timestamp timestamp = rs.getTimestamp("my_timestamp");//ResultSet rs
 *      //LocalDateTime localDateTime = timestamp.toLocalDateTime();
 *      LocalDateTime localDateTime = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
 *  [2.1] LocalDateTime => Timestamp
 *      LocalDateTime localDateTime = LocalDateTime.now();
 *      Timestamp timestamp = Timestamp.valueOf(localDateTime);
 * [2.2] LocalDateTime => Timestamp
 *      LocalDateTime localDateTime = LocalDateTime.now();
 *      ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
 *      Timestamp timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());
 *      preparedStatement.setTimestamp(index, timestamp);
 * @refrence-doc
 * @gpt-promt
 */

@Data
@AllArgsConstructor
@Getter
@Setter
@ApiModel("工作节点信息表 | 基于雪花算法的分布式ID解决方案")
@TableName(WorkerNode.TABLE_NAME_PARAM)
public class WorkerNode extends AbstractEntity {

    /**
     * 本实体对应的数据库表名
     */
    public final static String TABLE_NAME_PARAM = "kd_worker_node";

    /**
     * 运行状态的数据库字段
     */
    public final static String RUNNING_STATUS_FIELD_PARAM = "running_status";

    @TableId(type = IdType.INPUT) //INPUT : 插入前自行设置主键 / AUTO : 数据库ID自增 / ASSIGN_ID : 雪花算法 / ASSIGN_UUID : 排除中划线的UUID
    private Long id;

    /** 应用服务名称 **/
    @TableField(value = "service_name", exist = true, jdbcType = JdbcType.VARCHAR)
    private String serviceName;

    /* 应用服务的实例名称 */
    @TableField
    private String serviceInstance;

    @TableField(value = "host_name")
    private String hostName;

    @TableField
    private String ip;

    @TableField
    private Integer port;

    /* 节点类型 */
    @TableField
    private String nodeType;

    /* 启动时间 */
    @TableField
    private Timestamp launchTime;

    @TableField
    private String runningStatus;

    @TableField
    private String workerId;

    @TableField
    private Integer dataCenterId;

    @TableField
    private String dataCenterCode;

    @TableField(exist = false)
    private String dataCenterName;
}
