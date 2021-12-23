package com.hell.action.template;

import com.hell.common.CheckMsg;
import com.hell.config.action.BaseAction;
import com.hell.core.common.utils.Utils;
import com.hell.core.exception.ValidationException;
import com.hell.dao.TemplateDao;
import com.hell.dto.request.AddTemplateRequest;
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

@RestController
@Api(tags = "template api")
public class AddTemplateAction implements BaseAction<AddTemplateRequest, AddTemplateResponse> {

    private TemplateDao templateDao;
    private SaveTemplateNodeService saveTemplateNodeService;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/template/addTemplate")
    @ApiOperation("添加模版")
    @Override
    public AddTemplateResponse execute(@Valid @RequestBody AddTemplateRequest request) throws Exception {

        WFTemplate t = templateDao.findByNameAndEntSeq(request.getName(), request.getEntId());
        if (ObjectUtils.isNotEmpty(t)) {
            throw new ValidationException(CheckMsg.VALIDATION_NAME_IS_EXIT);
        }

        WFTemplate template = new WFTemplate();
        template.setEntSeq(request.getEntId());
        template.setName(request.getName());
        template.setType(request.getType());

        TemplateInfo allNodeInfo = new TemplateInfo();
        allNodeInfo.setNodes(request.getNodes());
        allNodeInfo.setConnections(request.getConnections());
        template.setAllNodeInfo(Utils.objectToJson(allNodeInfo));

        //添加模版
        template = templateDao.save(template);

        //添加模版节点
        saveTemplateNodeService.saveNode(template, request.getNodes(), request.getConnections());

        AddTemplateResponse response = new AddTemplateResponse();
        BeanUtils.copyProperties(request, response);
        response.setId(template.getSeq());//模版seq
        response.setUpdateTime(template.getUpdateTime());

        return response;
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
