package com.hell.dto.response;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ItemResponse extends BaseRequest {

    private Integer id;
    private String trsName;
    private String trsId;
    private String authData;
    private Integer createUserId;
    private Integer entId;
    private String flowStatus;
    private Date updateTime;
    private String authStatus;
    private String describeInfo;
    private String errorCode;
    private String errorMsg;
}
