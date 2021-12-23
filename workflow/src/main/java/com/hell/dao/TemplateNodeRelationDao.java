package com.hell.dao;

import com.hell.table.WFTemplateNodeRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateNodeRelationDao extends JpaRepository<WFTemplateNodeRelation, Integer> {

    void deleteByTemplateSeqAndEntSeq(Integer templateId, Integer entId);

    List<WFTemplateNodeRelation> findByEntSeqAndTemplateSeqAndCurrentNodeId(Integer entSeq, Integer templateSeq, Long startNodeId);
}
