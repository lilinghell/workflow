package com.hell.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QryTemplateEntity {
    private Integer id;
    private String name;
    private String type;
    private Integer entId;
    private List<Node> nodes;
    private List<Connection> connections;
    private Date updateTime;
}
