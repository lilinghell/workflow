package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import com.hell.entity.SaveTransTemplateEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddTransTemplateRequest extends BaseRequest {
    @NotNull
    private List<SaveTransTemplateEntity> transTemplate;
}
