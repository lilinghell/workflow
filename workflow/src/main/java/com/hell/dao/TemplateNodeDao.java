package com.hell.dao;

import com.hell.table.WFTemplate;
import com.hell.table.WFTemplateNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateNodeDao extends JpaRepository<WFTemplateNode, Integer> {

    void deleteByTemplateSeqAndEntSeq(Integer templateId, Integer entId);

    WFTemplateNode findByNodeTypeAndTemplateSeq(String nodeTypeStart, Integer templateSeq);

    WFTemplateNode findByNodeId(Long nextNodeId);
}
