package com.hell.dao;

import com.hell.dto.request.QryItemRequest;
import com.hell.dto.response.ItemResponse;
import com.hell.table.WFItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemDao extends JpaRepository<WFItem, Integer> {
    @Query(value = "select item.seq as id, item.trs_name as trsName, item.trs_id as trsId, " +
            "item.flow_Status as flowStatus, item.auth_data as authData, " +
            "item.ent_seq as entId, item.update_time as updateTime, item.create_user_seq as createUserId, " +
            "item.auth_status as authStatus, item.describe_info as describeInfo," +
            "item.error_code as errorCode, item.error_msg as errorMsg" +
            " from WF_Item item" +
            " where item.ent_seq = :#{#request.entId}" +
            " and if(:#{#request.trsName} != '', item.trs_name = :#{#request.trsName}, 1=1)" +
            " and if(:#{#request.trsId} != '', item.trs_id = :#{#request.trsId}, 1=1)" +
            " and if(:#{#request.flowStatus} != '', item.flow_status = :#{#request.flowStatus}, 1=1)" +
            " and if(IFNULL(:#{#request.createUserId}, '') != '', item.create_userId = :#{#request.createUserId}, 1=1)",
            nativeQuery = true)
    List<ItemResponse> qryItems(@Param("request") QryItemRequest request);
}
