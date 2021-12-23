package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateWorkBenchRequest extends BaseRequest {
    @NotNull
    private List<Integer> authId;
    @NotNull
    private Integer entId;
    @NotNull
    private String authStatus;
    private String describeInfo;
    @NotNull
    private Integer authUserId;
}
