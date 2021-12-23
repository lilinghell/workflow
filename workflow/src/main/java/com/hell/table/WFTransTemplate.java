package com.hell.table;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wf_trans_template",
        indexes = {@Index(columnList = "entSeq, transId", unique = true)},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"entSeq", "transId"})}
)
@org.hibernate.annotations.Table(appliesTo = "wf_trans_template", comment = "交易对应的审核模版表")
public class WFTransTemplate extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(128) comment '交易名称'")
    private String transName;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '交易ID'")
    private String transId;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'apiPath'")
    private String apiPath;//最后审批最终状态调用的API路径(通过、驳回、终止)

    @Column
    private Integer templateSeq;

    @Column(nullable = false)
    private Integer entSeq;
}
