package com.hell.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TemplateInfo implements Serializable {
    private List<Node> nodes;
    private List<Connection> connections;
}
