package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import com.hell.entity.Connection;
import com.hell.entity.Node;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateTemplateRequest extends BaseRequest {
    @NotNull
    private Integer templateId;
    @NotBlank
    private String name;
    private String type;
    @NotNull
    private Integer entId;
    private List<Node> nodes;
    private List<Connection> connections;
}
