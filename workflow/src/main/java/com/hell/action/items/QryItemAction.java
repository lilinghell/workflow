package com.hell.action.items;

import com.hell.config.action.BaseAction;
import com.hell.dao.ItemDao;
import com.hell.dto.request.QryItemRequest;
import com.hell.dto.response.ItemResponse;
import com.hell.dto.response.QryItemResponse;
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
@Api(tags = "items api")
public class QryItemAction implements BaseAction<QryItemRequest, QryItemResponse> {

    private ItemDao itemDao;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path = "/api/t1/item/qryItem")
    @ApiOperation("工单查询")
    @Override
    public QryItemResponse execute(@Valid @RequestBody QryItemRequest request) throws Exception {
        QryItemResponse r = new QryItemResponse();
        List<ItemResponse> list = itemDao.qryItems(request);
        r.setItems(list);
        return r;
    }


    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
