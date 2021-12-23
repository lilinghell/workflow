package com.hell.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

//审批者
@Data
public class Approver implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
}
