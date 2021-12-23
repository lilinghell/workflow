package com.hell.dto.response;

import com.hell.config.request.BaseRequest;
import com.hell.entity.WorkBenchEntity;
import lombok.Data;

import java.util.List;

@Data
public class QryWorkBenchResponse extends BaseRequest {
    private List<WorkBenchEntity> authRecords;


}
