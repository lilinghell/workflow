package com.hell.dto.response;

import com.hell.config.response.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class QryItemResponse extends BaseResponse {
    private List<ItemResponse> items;
}
