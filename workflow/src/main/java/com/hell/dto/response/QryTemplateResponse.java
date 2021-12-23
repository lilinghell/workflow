package com.hell.dto.response;

import com.hell.config.response.BaseResponse;
import com.hell.entity.QryTemplateEntity;
import lombok.Data;

import java.util.List;

@Data
public class QryTemplateResponse extends BaseResponse {
    private List<QryTemplateEntity> templates;
}
