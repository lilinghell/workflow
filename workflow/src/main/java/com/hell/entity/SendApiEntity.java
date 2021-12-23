package com.hell.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendApiEntity implements Serializable {
    private String authStatus;
    private String authData;
    private String trsName;
    private String trsId;
    private String describeInfo;//驳回原因、终止原因
}
