package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import com.hell.entity.DelTransTemplateEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DelTransTemplateRequest extends BaseRequest {

    @NotNull
    private List<DelTransTemplateEntity> delTransTemplates;
}
