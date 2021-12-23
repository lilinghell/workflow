package com.hell.action.workbench;

import com.hell.common.CheckMsg;
import com.hell.common.Dict;
import com.hell.config.action.BaseAction;
import com.hell.core.exception.ValidationException;
import com.hell.dao.WorkBenchDao;
import com.hell.dto.request.UpdateWorkBenchRequest;
import com.hell.dto.response.QryWorkBenchResponse;
import com.hell.entity.WorkBenchEntity;
import com.hell.service.WorkBenchService;
import com.hell.table.WFWorkBench;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "work bench api")
public class UpdateWorkBenchAction implements BaseAction<UpdateWorkBenchRequest, QryWorkBenchResponse> {

    private WorkBenchDao workBenchDao;
    private WorkBenchService workBenchService;

    @PostMapping(path = "/api/t1/workbench/approve")
    @ApiOperation("批准")
    @Override
    public QryWorkBenchResponse execute(@Valid @RequestBody UpdateWorkBenchRequest request) throws Exception {

        String authStatus = request.getAuthStatus();
        if (!authStatus.equals(Dict.AUTH_STATUS_0) && !authStatus.equals(Dict.AUTH_STATUS_1)
                && !authStatus.equals(Dict.AUTH_STATUS_2)) {
            throw new ValidationException(CheckMsg.VALIDATION_AUTH_STATUS_ERROR);
        }
        if (request.getAuthId().size() > 10) {
            throw new ValidationException(CheckMsg.VALIDATION_AUTH_TOO_MANY);
        }
        //审批
        List<WFWorkBench> workBenchList = workBenchDao.findByAuthUserSeqAndEntSeqAndSeqIn(request.getAuthUserId(),
                request.getEntId(), request.getAuthId());

        List<WorkBenchEntity> re = workBenchService.auth(workBenchList, request.getAuthStatus(),
                request.getDescribeInfo());

        QryWorkBenchResponse r = new QryWorkBenchResponse();
        r.setAuthRecords(re);
        return r;
    }
    @Autowired
    public void setWorkBenchDao(WorkBenchDao workBenchDao) {
        this.workBenchDao = workBenchDao;
    }

    @Autowired
    public void setWorkBenchService(WorkBenchService workBenchService) {
        this.workBenchService = workBenchService;
    }
}
