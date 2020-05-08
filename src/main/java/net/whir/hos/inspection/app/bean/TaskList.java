package net.whir.hos.inspection.app.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务表
 *
 * @author plf 2019年5月5日上午11:19:57
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TASK_LIST")
@ApiModel("定时任务")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID-数据库自增", required = false, example = "1")
    private Integer id;

    /**
     * 任务表达式
     */
    @ApiModelProperty(value = "任务表达式", required = true, example = "*/5 * * * * ?")
    private String cron;

    /**
     * 任务组名称
     */
    @ApiModelProperty(value = "任务组名称", required = true, example = "com.plf.task.scheduled.job.MyRunnable")
    private String clazz;

    /**
     * 状态
     * 0 代表 删除
     * 1 代表 启动
     * 2 代表 停止
     */
    @ApiModelProperty(value = "任务状态 0 删除 1启动 2 停止", required = false, example = "0")
    private Integer status;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "任务名", required = true, example = "任务一")
    private String taskName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false, example = "2019-05-07 12:00:00")
    private Date createTime;

    @ApiModelProperty(value = "对应的统一消息提醒ID")
    private Long unifiedId;

    @ApiModelProperty(value = "对应的漏检消息提醒ID")
    private Long missedId;

}
