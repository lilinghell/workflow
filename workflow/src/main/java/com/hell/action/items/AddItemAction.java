package com.hell.action.items;

import com.hell.common.Dict;
import com.hell.config.action.BaseAction;
import com.hell.dao.ItemDao;
import com.hell.dto.request.AddItemRequest;
import com.hell.dto.response.ItemResponse;
import com.hell.service.WorkBenchService;
import com.hell.table.WFItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "items api")
public class AddItemAction implements BaseAction<AddItemRequest, ItemResponse> {

    private ItemDao itemDao;
    private WorkBenchService workBenchService;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/item/createItem")
    @ApiOperation("制单")
    @Override
    public ItemResponse execute(@Valid @RequestBody AddItemRequest request) throws Exception {

        WFItem item = new WFItem();
        item.setEntSeq(request.getEntId());
        item.setAuthData(request.getAuthData());
        item.setTrsId(request.getTrsId());
        item.setTrsName(request.getTrsName());
        item.setCreateUserSeq(request.getCreateUserId());
        item.setFlowStatus(Dict.FLOW_STATUS_1);//审批中
        item = itemDao.save(item);

        ItemResponse r = new ItemResponse();
        r.setId(item.getSeq());
        r.setAuthData(item.getAuthData());
        r.setTrsId(item.getTrsId());
        r.setTrsName(item.getTrsName());
        r.setCreateUserId(item.getCreateUserSeq());
        r.setEntId(item.getEntSeq());
        r.setUpdateTime(item.getUpdateTime());
        r.setFlowStatus(item.getFlowStatus());

        //初始化workbench
        workBenchService.initWorkBench(item);

        return r;
    }


    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    public void setWorkBenchService(WorkBenchService workBenchService) {
        this.workBenchService = workBenchService;
    }
}
