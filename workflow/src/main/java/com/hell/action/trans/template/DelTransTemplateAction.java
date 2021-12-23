package com.hell.action.trans.template;

import com.hell.common.CheckMsg;
import com.hell.config.action.BaseAction;
import com.hell.config.response.ResponseResult;
import com.hell.core.exception.ValidationException;
import com.hell.dao.TransTemplateDao;
import com.hell.dao.WorkBenchDao;
import com.hell.dto.request.DelTransTemplateRequest;
import com.hell.entity.DelTransTemplateEntity;
import com.hell.table.WFTransTemplate;
import com.hell.table.WFWorkBench;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "trans template api")
public class DelTransTemplateAction implements BaseAction<DelTransTemplateRequest, ResponseResult> {

    private TransTemplateDao transTemplateDao;
    private WorkBenchDao workBenchDao;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/transTemplate/delTransTemplate")
    @ApiOperation("删除某交易(可多个)对应的模版")
    @Override
    public ResponseResult execute(@Valid @RequestBody DelTransTemplateRequest request) throws Exception {
        for (DelTransTemplateEntity entity : request.getDelTransTemplates()) {
            WFTransTemplate t = transTemplateDao.findByTransIdAndEntSeq(entity.getTransId(), entity.getEntId());
            if (ObjectUtils.isEmpty(t)) {
                throw new ValidationException(CheckMsg.VALIDATION_TRANSTEMPLATE_NOT_EXIT);
            }
            List<WFWorkBench> workBenchList = workBenchDao.findByTransTemplateSeq(t.getSeq());
            if (ObjectUtils.isNotEmpty(workBenchList)) {
                throw new ValidationException(CheckMsg.VALIDATION_NOT_DEL);
            }
            transTemplateDao.deleteById(t.getSeq());
        }

        return new ResponseResult("Success");
    }

    @Autowired
    public void setTransTemplateDao(TransTemplateDao transTemplateDao) {
        this.transTemplateDao = transTemplateDao;
    }

    @Autowired
    public void setWorkBenchDao(WorkBenchDao workBenchDao) {
        this.workBenchDao = workBenchDao;
    }
}
