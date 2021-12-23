package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QryTemplateRequest extends BaseRequest {

    private Integer id;
    @NotNull
    private Integer entId;
}