package com.hell.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkBenchEntity implements Serializable {
    private Integer authId;//审批ID
    private Integer itemId;//工单ID
    private String flowStatus;//流程状态 0:流程结束 1:审批中
    private Integer authUserId;//审批人
    private String authStatus;//审批状态 审批状态0:通过,1:驳回,2:终止,3:待审批
    private String describeInfo;//驳回原因、终止原因
    private String errorCode;//异常码
    private String errorMsg;//异常信息
    private Integer entId;//企业seq
}
