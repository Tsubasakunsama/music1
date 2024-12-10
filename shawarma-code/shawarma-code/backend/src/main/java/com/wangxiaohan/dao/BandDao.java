package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Band;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BandDao extends Mapper<Band> {
    List<Band> findBySearch(@Param("params") Params params);

    @Select("SELECT * FROM band WHERE bname=#{bname} LIMIT 1")
    Band findByBname(@Param("bname") String bname);
}

