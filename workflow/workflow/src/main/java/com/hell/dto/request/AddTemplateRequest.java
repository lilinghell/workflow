package com.hell.dto.request;

import com.hell.config.request.BaseRequest;
import com.hell.entity.Connection;
import com.hell.entity.Node;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddTemplateRequest extends BaseRequest {

    @NotBlank
    private String name;
    private String type;
    @NotNull
    private Integer entId;
    @Valid
    private List<Node> nodes;
    @Valid
    private List<Connection> connections;
}
