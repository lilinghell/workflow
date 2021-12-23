package com.hell.table;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wf_template", indexes ={@Index(columnList = "entSeq, name", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "wf_template", comment = "审核模版表")
public class WFTemplate extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(128) comment '模版名称'")
    private String name;

    @Lob
    @Column(nullable = false)
    private String allNodeInfo;//所有节点信息

    @Column(columnDefinition = "varchar(12) comment '模版类型'")
    private String type;

    @Column(nullable = false)
    private Integer entSeq;
}
