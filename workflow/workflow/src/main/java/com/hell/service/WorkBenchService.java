package com.hell.service;

import com.hell.common.CheckMsg;
import com.hell.common.Dict;
import com.hell.core.exception.ValidationException;
import com.hell.dao.*;
import com.hell.entity.SendApiEntity;
import com.hell.entity.WorkBenchEntity;
import com.hell.table.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkBenchService {
    protected Log log = LogFactory.getLog(this.getClass());
    private TransTemplateDao transTemplateDao;
    private TemplateNodeDao templateNodeDao;
    private TemplateNodeRelationDao templateNodeRelationDao;
    private WorkBenchDao workBenchDao;
    private ItemDao itemDao;

    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Transactional(rollbackFor = Exception.class)
    public void initWorkBench(WFItem item) throws Exception {
        WFWorkBench workBench = new WFWorkBench();
        workBench.setEntSeq(item.getEntSeq());
        workBench.setAuthStatus(Dict.AUTH_STATUS_3);//待审批
        workBench.setItemSeq(item.getSeq());
        WFTransTemplate wfTransTemplate = transTemplateDao.findByTransIdAndEntSeq(item.getTrsId(), item.getEntSeq());
        if (ObjectUtils.isEmpty(wfTransTemplate)) {
            throw new ValidationException(CheckMsg.VALIDATION_TRANSTEMPLATE_NOT_EXIT);
        }
        workBench.setTransTemplateSeq(wfTransTemplate.getSeq());

        //查询当前的审批节点和审批人
        //1、获取start节点Id
        WFTemplateNode templateNode = templateNodeDao.findByNodeTypeAndTemplateSeq(Dict.NODE_TYPE_START, wfTransTemplate.getTemplateSeq());
        Long startNodeId = templateNode.getNodeId();
        //2、查找第一个活动节点
        List<WFTemplateNodeRelation> nodeRelations = templateNodeRelationDao.findByEntSeqAndTemplateSeqAndCurrentNodeId(templateNode.getEntSeq(), templateNode.getTemplateSeq(), startNodeId);
        if (ObjectUtils.isEmpty(nodeRelations)) {
            throw new ValidationException(CheckMsg.VALIDATION_OPERATION_NOT_EXIT);
        }
        for (WFTemplateNodeRelation relation : nodeRelations) {
            WFTemplateNode node = templateNodeDao.findByNodeId(relation.getNextNodeId());
            if (!node.getNodeType().equals(Dict.NODE_TYPE_OPERATION)) {
                throw new ValidationException(CheckMsg.VALIDATION_OPERATION_NOT_EXIT);
            }
            Integer authUserSeq = node.getAuthUserSeq();
            if (ObjectUtils.isEmpty(authUserSeq)) {
                throw new ValidationException(CheckMsg.VALIDATION_AUTHUSER_NOT_EXIT);
            }
            workBench.setAuthUserSeq(authUserSeq);
            workBench.setCurrentNodeId(node.getNodeId());
            workBenchDao.save(workBench);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<WorkBenchEntity> auth(List<WFWorkBench> workBenchList, String authStatus, String desc) throws Exception {
        if (workBenchList.size() > 10) {
            throw new ValidationException(CheckMsg.VALIDATION_AUTH_TOO_MANY);
        }
        List<WorkBenchEntity> re = new ArrayList<>();
        for (WFWorkBench workBench : workBenchList) {
            WorkBenchEntity workBenchEntity = new WorkBenchEntity();
            boolean send = true;
            try {
                log.error("====workBench:" + workBench.getSeq() + ",authStatus:" + authStatus);
                if (null == workBench || !workBench.getAuthStatus().equals(Dict.AUTH_STATUS_3)) {
                    throw new ValidationException(CheckMsg.VALIDATION_NOT_EXIT);
                }
                workBench.setAuthStatus(authStatus);
                workBench.setDescribeInfo(desc);
                if (authStatus.equals(Dict.AUTH_STATUS_0)) {//通过
                    //查询下一个节点的审批人
                    send = nextWorkBench(workBench);
                }
                if (send) {
                    //驳回、终止、流程结束
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            sendApi(workBench);
                        }
                    });
                }
            } catch (Exception e) {
                log.error("====workBench error:" + workBench.getSeq() + ":" + e.getStackTrace());
                workBench.setErrorCode("999901");
                workBench.setErrorMsg("审批异常,请联系管理员");
                e.printStackTrace();
            } finally {
                workBenchDao.save(workBench);
                workBenchEntity.setAuthId(workBench.getSeq());
                workBenchEntity.setAuthStatus(workBench.getAuthStatus());
                if (send) {//流程结束
                    workBenchEntity.setFlowStatus(Dict.FLOW_STATUS_0);
                }
                workBenchEntity.setDescribeInfo(workBench.getDescribeInfo());
                workBenchEntity.setEntId(workBench.getEntSeq());
                workBenchEntity.setAuthUserId(workBench.getAuthUserSeq());
                workBenchEntity.setItemId(workBench.getItemSeq());
                workBenchEntity.setErrorCode(workBench.getErrorCode());
                workBenchEntity.setErrorMsg(workBench.getErrorMsg());
                re.add(workBenchEntity);
            }
        }
        return re;
    }


    private boolean nextWorkBench(WFWorkBench currentWorkBench) throws Exception {
        Long currentNode = currentWorkBench.getCurrentNodeId();
        WFTransTemplate transTemplate = transTemplateDao.findById(currentWorkBench.getTransTemplateSeq()).get();
        List<WFTemplateNodeRelation> nodeRelations =
                templateNodeRelationDao.findByEntSeqAndTemplateSeqAndCurrentNodeId(currentWorkBench.getEntSeq(),
                        transTemplate.getTemplateSeq(), currentNode);
        if (ObjectUtils.isEmpty(nodeRelations)) {
            throw new ValidationException(CheckMsg.VALIDATION_OPERATION_NOT_EXIT);
        }
        boolean send = true;
        for (WFTemplateNodeRelation relation : nodeRelations) {
            WFTemplateNode nextNode = templateNodeDao.findByNodeId(relation.getNextNodeId());
            if (!nextNode.getNodeType().equals(Dict.NODE_TYPE_END)) {
                //下一个不是end节点
                send = false;
                WFWorkBench workBench = new WFWorkBench();
                workBench.setEntSeq(currentWorkBench.getEntSeq());
                workBench.setAuthStatus(Dict.AUTH_STATUS_3);
                workBench.setCurrentNodeId(nextNode.getNodeId());
                workBench.setAuthUserSeq(nextNode.getAuthUserSeq());
                workBench.setTransTemplateSeq(currentWorkBench.getTransTemplateSeq());
                workBench.setItemSeq(currentWorkBench.getItemSeq());
                workBenchDao.save(workBench);
            } else {
                //下一个节点是end，判断同一个工单是否还有 1:驳回,2:终止,3:待审批
                List<String> status = new ArrayList<>();
                status.add("1");
                status.add("2");
                status.add("3");
                List<WFWorkBench> other = workBenchDao.findByItemSeqAndSeqNotAndAuthStatusIn(
                        currentWorkBench.getItemSeq(),
                        currentWorkBench.getSeq(), status);
                if (ObjectUtils.isNotEmpty(other)) {
                    send = false;
                }
            }
        }
        return send;
    }

    private void sendApi(WFWorkBench workBench) {

        WFTransTemplate transTemplate = transTemplateDao.findById(workBench.getTransTemplateSeq()).get();
        String apiPath = transTemplate.getApiPath();
        WFItem item = itemDao.findById(workBench.getItemSeq()).get();
        String authData = item.getAuthData();

        SendApiEntity sendApiEntity = new SendApiEntity();
        sendApiEntity.setAuthStatus(workBench.getAuthStatus());
        sendApiEntity.setAuthData(authData);
        sendApiEntity.setTrsName(item.getTrsName());
        sendApiEntity.setTrsId(item.getTrsId());
        sendApiEntity.setDescribeInfo(workBench.getDescribeInfo());

        item.setAuthStatus(workBench.getAuthStatus());
        try {
            if (workBench.getAuthStatus().equals(Dict.AUTH_STATUS_0)) {
                //只有通过才进行调用业务API todo
                ///
                ////
            }
        } catch (Exception e) {
            log.error("=====send api error===" + e);
            item.setErrorCode("999900");
            item.setErrorMsg("send api error");
            e.printStackTrace();
        }
        item.setFlowStatus(Dict.FLOW_STATUS_0);
        //最终状态，更新工单信息
        itemDao.save(item);
    }

    @Autowired
    public void setTemplateNodeDao(TemplateNodeDao templateNodeDao) {
        this.templateNodeDao = templateNodeDao;
    }

    @Autowired
    public void setWorkBenchDao(WorkBenchDao workBenchDao) {
        this.workBenchDao = workBenchDao;
    }

    @Autowired
    public void setTemplateNodeRelationDao(TemplateNodeRelationDao templateNodeRelationDao) {
        this.templateNodeRelationDao = templateNodeRelationDao;
    }

    @Autowired
    public void setTransTemplateDao(TransTemplateDao transTemplateDao) {
        this.transTemplateDao = transTemplateDao;
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
