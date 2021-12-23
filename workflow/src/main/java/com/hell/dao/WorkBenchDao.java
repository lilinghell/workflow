package com.hell.dao;

import com.hell.entity.WorkBenchEntity;
import com.hell.table.WFWorkBench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkBenchDao extends JpaRepository<WFWorkBench, Integer> {
    List<WFWorkBench> findByTransTemplateSeq(Integer seq);

    @Query(value = "select bench.seq as authId, item.seq as itemId, item.auth_data as authData," +
            " item.create_user_seq as createUserId, item.create_time as itemCreateTime," +
            " item.update_time as itemUpdateTime, item.flow_status as flowStatus," +
            " bench.auth_User_Seq as authUserId,  bench.auth_status as authStatus," +
            " bench.describe_info as describeInfo,  bench.error_code as errorCode," +
            " bench.error_msg as errorMsg, bench.ent_id as entId," +
            " bench.create_time as createTime, bench.update_time as updateTime" +
            " from wf_work_bench as bench, wf_item as item" +
            " where bench.item_seq = item.seq" +
            " and bench.ent_id = :entId" +
            " and bench.auth_user_seq = :authUserId" +
            " and if(:authStatus != '', bench.auth_status = :authStatus, 1=1)", nativeQuery = true)
    List<WorkBenchEntity> qryMyAuthRecords(String authStatus, Integer authUserId, Integer entId);

    List<WFWorkBench> findByAuthUserSeqAndEntSeqAndSeqIn(Integer authUserId, Integer entId, List<Integer> authId);

    List<WFWorkBench> findByItemSeqAndSeqNotAndAuthStatusIn(Integer itemSeq, Integer seq, List<String> status);
}
