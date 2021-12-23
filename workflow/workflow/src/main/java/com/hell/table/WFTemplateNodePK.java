package com.hell.table;

import lombok.Data;

import java.io.Serializable;

@Data
public class WFTemplateNodePK implements Serializable {
    private Integer entSeq;
    private Integer templateSeq;
    private Long nodeId;
}
