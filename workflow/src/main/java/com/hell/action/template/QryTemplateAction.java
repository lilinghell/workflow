package com.hell.action.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hell.config.action.BaseAction;
import com.hell.core.common.utils.Utils;
import com.hell.dao.TemplateDao;
import com.hell.dto.request.QryTemplateRequest;
import com.hell.dto.response.QryTemplateResponse;
import com.hell.entity.QryTemplateEntity;
import com.hell.entity.TemplateInfo;
import com.hell.table.WFTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "template api")
public class QryTemplateAction implements BaseAction<QryTemplateRequest, QryTemplateResponse> {
    private TemplateDao templateDao;

    @PostMapping(path = "/api/t1/template/qryTemplate")
    @ApiOperation("查询模版")
    @Override
    public QryTemplateResponse execute(@Valid @RequestBody QryTemplateRequest request) throws Exception {
        List<WFTemplate> templateList = new ArrayList<>();
        if(ObjectUtils.isEmpty(request.getId())) {
            templateList = templateDao.findByEntSeq(request.getEntId());
        } else {
            templateList = templateDao.findBySeqAndEntSeq(request.getId(), request.getEntId());
        }

        QryTemplateResponse response = new QryTemplateResponse();
        List<QryTemplateEntity> list = new ArrayList<>();
        templateList.forEach(template -> {
            QryTemplateEntity qryTemplateEntity = new QryTemplateEntity();
            qryTemplateEntity.setId(template.getSeq());
            qryTemplateEntity.setName(template.getName());
            qryTemplateEntity.setType(template.getType());
            qryTemplateEntity.setEntId(template.getEntSeq());
            String allNodeINfo = template.getAllNodeInfo();
            TemplateInfo templateInfo = null;
            try {
                templateInfo = Utils.jsonToPojo(allNodeINfo, TemplateInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            qryTemplateEntity.setNodes(templateInfo.getNodes());
            qryTemplateEntity.setConnections(templateInfo.getConnections());
            qryTemplateEntity.setUpdateTime(template.getUpdateTime());
            list.add(qryTemplateEntity);
        });
        response.setTemplates(list);
        return response;
    }

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }
}
