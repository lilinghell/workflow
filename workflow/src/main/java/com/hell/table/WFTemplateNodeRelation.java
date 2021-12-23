package com.hell.table;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "wf_template_node_relation", indexes = {@Index(columnList = "entSeq,templateSeq,currentNodeId")})
@org.hibernate.annotations.Table(appliesTo = "wf_template_node_relation", comment = "各流程节点关系表")
public class WFTemplateNodeRelation extends BaseEntity {

    @Column(nullable = false)
    private Long currentNodeId;//当前步骤ID
    @Column(nullable = false)
    private Long nextNodeId;//下一节点ID
    @Column(nullable = false)
    private Integer templateSeq;

    @Column(nullable = false)
    private Integer entSeq;
}
