package com.hell.action.template;

import com.hell.common.CheckMsg;
import com.hell.config.action.BaseAction;
import com.hell.config.response.ResponseResult;
import com.hell.core.exception.ValidationException;
import com.hell.dao.TemplateDao;
import com.hell.dao.TemplateNodeDao;
import com.hell.dao.TemplateNodeRelationDao;
import com.hell.dao.TransTemplateDao;
import com.hell.dto.request.DelTemplateRequest;
import com.hell.table.WFTransTemplate;
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
@Api(tags = "template api")
public class DelTemplateAction implements BaseAction<DelTemplateRequest, ResponseResult> {
    private TemplateDao templateDao;
    private TransTemplateDao transTemplateDao;
    private TemplateNodeDao templateNodeDao;
    private TemplateNodeRelationDao templateNodeRelationDao;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/template/delTemplate")
    @ApiOperation("模版删除")
    @Override
    public ResponseResult execute(@Valid @RequestBody DelTemplateRequest request) throws Exception {

        List<WFTransTemplate> wfTransTemplate = transTemplateDao.findByTemplateSeqAndEntSeq(request.getTemplateId(), request.getEntId());
        if (ObjectUtils.isNotEmpty(wfTransTemplate)) {
            throw new ValidationException(CheckMsg.VALIDATION_NOT_DEL);
        }
        templateDao.deleteBySeqAndEntSeq(request.getTemplateId(), request.getEntId());
        templateNodeDao.deleteByTemplateSeqAndEntSeq(request.getTemplateId(), request.getEntId());
        templateNodeRelationDao.deleteByTemplateSeqAndEntSeq(request.getTemplateId(), request.getEntId());

        return new ResponseResult("Success");
    }

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Autowired
    public void setTransTemplateDao(TransTemplateDao transTemplateDao) {
        this.transTemplateDao = transTemplateDao;
    }

    @Autowired
    public void setTemplateNodeDao(TemplateNodeDao templateNodeDao) {
        this.templateNodeDao = templateNodeDao;
    }

    @Autowired
    public void setTemplateNodeRelationDao(TemplateNodeRelationDao templateNodeRelationDao) {
        this.templateNodeRelationDao = templateNodeRelationDao;
    }
}
