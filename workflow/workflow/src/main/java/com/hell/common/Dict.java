package com.hell.common;

public class Dict {
    //审批状态0:通过,1:驳回,2:终止,3:待审批
    public static final String AUTH_STATUS_0 = "0";//通过
    public static final String AUTH_STATUS_1 = "1";//驳回
    public static final String AUTH_STATUS_2 = "2";//终止
    public static final String AUTH_STATUS_3 = "3";//待审批
    //流程状态 0:流程结束 1:审批中
    public static final String FLOW_STATUS_0 = "0";//流程结束
    public static final String FLOW_STATUS_1 = "1";//审批中
    //节点类型
    public static final String NODE_TYPE_START = "start";//开始节点
    public static final String NODE_TYPE_OPERATION = "operation";//活动节点
    public static final String NODE_TYPE_END = "end";//结束节点

}
