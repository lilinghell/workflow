package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddItemRequest extends BaseRequest {

    @NotBlank
    private String trsName;
    @NotBlank
    private String trsId;
    private String authData;
    @NotNull
    private Integer createUserId;
    @NotNull
    private Integer entId;
}
