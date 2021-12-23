package com.hell.table;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "wf_work_bench", indexes = {
        @Index(columnList = "entSeq,authUserSeq,authStatus")
})
@org.hibernate.annotations.Table(appliesTo = "wf_work_bench", comment = "审批记录表")
public class WFWorkBench extends BaseEntity {

    @Column(nullable = false)
    private Integer itemSeq;//工单

    @Column(nullable = false)
    private Integer transTemplateSeq;//该工单使用的是哪个交易模版

    @Column(nullable = false)
    private Long currentNodeId;//当前模版节点ID

    @Column(nullable = false)
    private Integer authUserSeq;//审批人

    @Column(nullable = false, columnDefinition = "varchar(1) comment '审批状态0:通过,1:驳回,2:终止,3:待审批'")
    private String authStatus;//审批状态

    @Column(columnDefinition = "varchar(128) comment '描述'")
    private String describeInfo;//驳回原因、终止原因

    @Column(columnDefinition = "varchar(64) comment '异常码'")
    private String errorCode;//异常码

    @Column(columnDefinition = "varchar(64) comment '异常信息'")
    private String errorMsg;//异常信息
    
    @Column(nullable = false)
    private Integer entSeq;//企业seq
}
