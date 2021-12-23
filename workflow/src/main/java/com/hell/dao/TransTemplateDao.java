package com.hell.dao;

import com.hell.table.WFTransTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransTemplateDao extends JpaRepository<WFTransTemplate, Integer> {
    List<WFTransTemplate> findByTemplateSeqAndEntSeq(Integer templateId, Integer entId);

    WFTransTemplate findByTransIdAndEntSeq(String transId, Integer entSeq);
}
