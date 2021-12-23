package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QryItemRequest extends BaseRequest {
    private String trsName;
    private String trsId;
    private String flowStatus;
    private Integer createUserId;
    @NotNull
    private Integer entId;
}
