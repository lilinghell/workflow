package com.hell.dto.response;

import com.hell.config.response.BaseResponse;
import com.hell.entity.Connection;
import com.hell.entity.Node;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddTemplateResponse extends BaseResponse {

    private Integer id;
    private String name;
    private String type;
    private Integer entId;
    private List<Node> nodes;
    private List<Connection> connections;
    private Date updateTime;
}
