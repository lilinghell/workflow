package com.hell.dao;

import com.hell.table.WFTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateDao extends JpaRepository<WFTemplate, Integer> {

    List<WFTemplate> findByEntSeq(Integer entId);

    List<WFTemplate> findBySeqAndEntSeq(Integer id, Integer entId);

    void deleteBySeqAndEntSeq(Integer templateId, Integer entId);

    WFTemplate findByNameAndEntSeq(String name, Integer entId);
}
