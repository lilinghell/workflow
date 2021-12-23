package com.hell.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class Node implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;//start、end、operation
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private List<Approver> approvers;//审批者
}
