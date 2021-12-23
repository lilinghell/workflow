package com.hell.action.trans.template;

import com.hell.common.CheckMsg;
import com.hell.config.action.BaseAction;
import com.hell.config.response.ResponseResult;
import com.hell.core.exception.ValidationException;
import com.hell.dao.TemplateDao;
import com.hell.dao.TransTemplateDao;
import com.hell.dto.request.AddTransTemplateRequest;
import com.hell.entity.SaveTransTemplateEntity;
import com.hell.table.WFTemplate;
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
@Api(tags = "trans template api")
public class SaveTransTemplateAction implements BaseAction<AddTransTemplateRequest, ResponseResult> {

    private TransTemplateDao transTemplateDao;
    private TemplateDao templateDao;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/transTemplate/saveTransTemplate")
    @ApiOperation("新增或更新某交易(可多个)对应的模版")
    @Override
    public ResponseResult execute(@Valid @RequestBody AddTransTemplateRequest request) throws Exception {
        List<SaveTransTemplateEntity> transTemplateEntityList = request.getTransTemplate();
        for (SaveTransTemplateEntity entity : transTemplateEntityList) {
            List<WFTemplate> templateList = templateDao.findBySeqAndEntSeq(entity.getTemplateId(), entity.getEntId());
            if(ObjectUtils.isEmpty(templateList)) {
                throw new ValidationException(CheckMsg.VALIDATION_TEMPLATE_NOT_EXIT);
            }
            WFTransTemplate transTemplate = transTemplateDao.findByTransIdAndEntSeq(entity.getTransId(), entity.getEntId());
            if (ObjectUtils.isNotEmpty(transTemplate)) {
                //更新
                transTemplate.setTemplateSeq(entity.getTemplateId());
                transTemplate.setTransName(entity.getTransName());
                transTemplate.setApiPath(entity.getApiPath());
            } else {
                //新增
                transTemplate = new WFTransTemplate();
                transTemplate.setTemplateSeq(entity.getTemplateId());
                transTemplate.setTransName(entity.getTransName());
                transTemplate.setTransId(entity.getTransId());
                transTemplate.setEntSeq(entity.getEntId());
                transTemplate.setApiPath(entity.getApiPath());
            }
            transTemplateDao.save(transTemplate);
        }
        return new ResponseResult("Success");
    }

    @Autowired
    public void setTransTemplateDao(TransTemplateDao transTemplateDao) {
        this.transTemplateDao = transTemplateDao;
    }

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }
}
