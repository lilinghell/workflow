package com.hell.action.template;

import com.hell.common.CheckMsg;
import com.hell.config.action.BaseAction;
import com.hell.core.common.utils.Utils;
import com.hell.core.exception.ValidationException;
import com.hell.dao.TemplateDao;
import com.hell.dto.request.UpdateTemplateRequest;
import com.hell.dto.response.AddTemplateResponse;
import com.hell.entity.TemplateInfo;
import com.hell.service.SaveTemplateNodeService;
import com.hell.table.WFTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "template api")
public class UpdateTemplateAction implements BaseAction<UpdateTemplateRequest, AddTemplateResponse> {
    private TemplateDao templateDao;
    private SaveTemplateNodeService saveTemplateNodeService;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/template/updateTemplate")
    @ApiOperation("模版删除")
    @Override
    public AddTemplateResponse execute(@Valid @RequestBody UpdateTemplateRequest request) throws Exception {
        WFTemplate t = templateDao.findByNameAndEntSeq(request.getName(), request.getEntId());
        if (ObjectUtils.isNotEmpty(t) && t.getSeq().compareTo(request.getTemplateId()) != 0) {
            throw new ValidationException(CheckMsg.VALIDATION_NAME_IS_EXIT);
        }

        AddTemplateResponse templateResponse = new AddTemplateResponse();
        List<WFTemplate> templates = templateDao.findBySeqAndEntSeq(request.getTemplateId(), request.getEntId());
        if (ObjectUtils.isNotEmpty(templates)) {
            WFTemplate template = templates.get(0);
            template.setType(request.getType());
            template.setName(request.getName());

            TemplateInfo allNodeInfo = new TemplateInfo();
            allNodeInfo.setNodes(request.getNodes());
            allNodeInfo.setConnections(request.getConnections());
            template.setAllNodeInfo(Utils.objectToJson(allNodeInfo));
            template = templateDao.save(template);//保存模版

            saveTemplateNodeService.updateNode(template, request.getNodes(), request.getConnections());

            BeanUtils.copyProperties(request, templateResponse);
            templateResponse.setId(template.getSeq());//模版seq
            templateResponse.setUpdateTime(template.getUpdateTime());
        }

        return templateResponse;
    }

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Autowired
    public void setSaveTemplateNodeService(SaveTemplateNodeService saveTemplateNodeService) {
        this.saveTemplateNodeService = saveTemplateNodeService;
    }
}
