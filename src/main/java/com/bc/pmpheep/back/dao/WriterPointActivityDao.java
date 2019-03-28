package com.bc.pmpheep.back.dao;


import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.WriterPointActivity;
import com.bc.pmpheep.back.vo.WriterPointVO;

import java.util.List;

public interface WriterPointActivityDao {

    List<WriterPointActivity> queryList(PageParameter<WriterPointActivity> parameter);

    int insert(WriterPointActivity record);

    WriterPointActivity selectById(Long id);

    int updateById(WriterPointActivity record);
    int queryListCount(PageParameter<WriterPointActivity> parameter);

}