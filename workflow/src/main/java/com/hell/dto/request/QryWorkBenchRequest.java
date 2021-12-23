package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QryWorkBenchRequest extends BaseRequest {
    private String authStatus;
    @NotNull
    private Integer authUserId;
    @NotNull
    private Integer entId;
}
