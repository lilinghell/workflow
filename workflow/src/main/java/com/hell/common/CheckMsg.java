package com.hell.common;

public class CheckMsg {
    public static final String VALIDATION_SYSTEM_ERROR = "validation.system.error"; //系统繁忙
    public static final String VALIDATION_RANGE = "validation.range"; //超出范围
    public static final String VALIDATION_NOT_DEL = "validation.not.del";//
    public static final String VALIDATION_NAME_IS_EXIT = "validation.name.is.exit";//名称已经存在
    public static final String VALIDATION_IS_EXIT = "validation.is.exit";//已经存在
    public static final String VALIDATION_TRANSTEMPLATE_NOT_EXIT = "validation.transTemplate.not.exit";//交易未指定审批流程
    public static final String VALIDATION_TEMPLATE_NOT_EXIT = "validation.template.not.exit";//流程模版不存在
    public static final String VALIDATION_OPERATION_NOT_EXIT = "validation.operation.not.exit";//没有活动节点，检查流程模版
    public static final String VALIDATION_AUTHUSER_NOT_EXIT = "validation.authUser.not.exit";//未指定审批人
    public static final String VALIDATION_NOT_EXIT = "validation.auth.not.exit";//不存在
    public static final String VALIDATION_AUTH_STATUS_ERROR = "validation.auth.status.error";//审批状态错误
    public static final String VALIDATION_AUTH_TOO_MANY = "validation.auth.too.many";
}