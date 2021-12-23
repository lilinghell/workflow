package com.hell.table;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "wf_item")
@org.hibernate.annotations.Table(appliesTo = "wf_item", comment = "制单表")
public class WFItem extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(128) comment '交易名称'")
    private String trsName;//交易名

    @Column(nullable = false, columnDefinition = "varchar(128) comment '交易ID'")
    private String trsId;//交易ID

    @Lob
    @Column
    private String authData;//待审批数据

    @Column(nullable = false, columnDefinition = "varchar(1) comment '0:流程结束 1:审批中'")
    private String flowStatus;//流程状态

    @Column(nullable = false)
    private Integer createUserSeq;//创建人

    @Column(nullable = false, columnDefinition = "varchar(1) comment '审批状态0:通过,1:驳回,2:终止,3:待审批'")
    private String authStatus;//审批状态

    @Column(columnDefinition = "varchar(128) comment '描述'")
    private String describeInfo;//驳回原因、终止原因

    @Column(columnDefinition = "varchar(64) comment '异常码'")
    private String errorCode;//异常码

    @Column(columnDefinition = "varchar(64) comment '异常信息'")
    private String errorMsg;//异常信息

    @Column(nullable = false)
    private Integer entSeq;
}
