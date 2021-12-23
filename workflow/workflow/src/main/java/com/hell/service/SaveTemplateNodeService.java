package com.hell.service;

import com.hell.dao.TemplateNodeDao;
import com.hell.dao.TemplateNodeRelationDao;
import com.hell.entity.Connection;
import com.hell.entity.Node;
import com.hell.table.WFTemplate;
import com.hell.table.WFTemplateNode;
import com.hell.table.WFTemplateNodeRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveTemplateNodeService {
    private TemplateNodeDao templateNodeDao;
    private TemplateNodeRelationDao templateNodeRelationDao;

    public void saveNode(WFTemplate template, List<Node> nodes, List<Connection> connections) {
        //添加模版节点
        saveTemplateNode(template, nodes);
        //添加模版节点关系
        saveTemplateNdeRelation(template, connections);
    }

    public void updateNode(WFTemplate template, List<Node> nodes, List<Connection> connections) {
        //删除节点
        templateNodeDao.deleteByTemplateSeqAndEntSeq(template.getSeq(), template.getEntSeq());
        templateNodeRelationDao.deleteByTemplateSeqAndEntSeq(template.getSeq(), template.getEntSeq());

        saveNode(template, nodes, connections);
    }

    private void saveTemplateNdeRelation(WFTemplate template, List<Connection> connections) {
        for (Connection connection : connections) {
            WFTemplateNodeRelation templateNodeRelation = new WFTemplateNodeRelation();
            templateNodeRelation.setTemplateSeq(template.getSeq());
            templateNodeRelation.setCurrentNodeId(connection.getSource().getId());
            templateNodeRelation.setEntSeq(template.getEntSeq());
            templateNodeRelation.setNextNodeId(connection.getDestination().getId());
            templateNodeRelationDao.save(templateNodeRelation);
        }
    }

    private void saveTemplateNode(WFTemplate template, List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            WFTemplateNode templateNode = new WFTemplateNode();
            templateNode.setNodeId(node.getId());
            templateNode.setTemplateSeq(template.getSeq());
            templateNode.setNodeName(node.getName());
            templateNode.setNodeType(node.getType());
            templateNode.setEntSeq(template.getEntSeq());
            if (null != node.getApprovers() && node.getApprovers().size() > 0) {
                templateNode.setAuthUserSeq(node.getApprovers().get(0).getId());
                templateNode.setAuthUserName(node.getApprovers().get(0).getName());
            }
            templateNodeDao.save(templateNode);
        }
    }

    @Autowired
    public void setTemplateNodeRelationDao(TemplateNodeRelationDao templateNodeRelationDao) {
        this.templateNodeRelationDao = templateNodeRelationDao;
    }

    @Autowired
    public void setTemplateNodeDao(TemplateNodeDao templateNodeDao) {
        this.templateNodeDao = templateNodeDao;
    }
}
