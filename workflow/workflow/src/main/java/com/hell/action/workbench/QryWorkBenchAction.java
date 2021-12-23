package com.hell.action.workbench;

import com.hell.config.action.BaseAction;
import com.hell.dao.WorkBenchDao;
import com.hell.dto.request.QryWorkBenchRequest;
import com.hell.dto.response.QryWorkBenchResponse;
import com.hell.entity.WorkBenchEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "work bench api")
public class QryWorkBenchAction implements BaseAction<QryWorkBenchRequest, QryWorkBenchResponse> {

    private WorkBenchDao workBenchDao;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/workbench/qryMyAuths")
    @ApiOperation("待审批记录")
    @Override
    public QryWorkBenchResponse execute(@Valid @RequestBody QryWorkBenchRequest request) throws Exception {
        QryWorkBenchResponse r = new QryWorkBenchResponse();
        List<WorkBenchEntity> list = workBenchDao.qryMyAuthRecords(request.getAuthStatus(),
                request.getAuthUserId(), request.getEntId());
        r.setAuthRecords(list);
        return r;
    }

    @Autowired
    public void setWorkBenchDao(WorkBenchDao workBenchDao) {
        this.workBenchDao = workBenchDao;
    }
}
