package com.hell.table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EntityListeners({AuditingEntityListener.class})
@Entity
@Table(name = "wf_template_node")
@IdClass(WFTemplateNodePK.class)
//模版中节点信息表
public class WFTemplateNode implements Serializable {

    @Id
    @Column(nullable = false)
    private Integer templateSeq;//模版

    @Id
    @Column(nullable = false)
    private Long nodeId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '流程节点名称'")
    private String nodeName;//流程节点名称
    @Column(nullable = false, columnDefinition = "varchar(32) comment '类型start、operation、end'")
    private String nodeType;//节点类型
    @Column
    private Integer authUserSeq;//审批人
    @Column
    private String authUserName;//审批人名称

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Id
    @Column(nullable = false)
    private Integer entSeq;
}
