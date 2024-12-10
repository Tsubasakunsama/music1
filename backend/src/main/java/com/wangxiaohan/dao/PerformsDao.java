package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Performs;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PerformsDao extends Mapper<Performs> {

    @Select("SELECT * FROM performs WHERE CID = #{CID} LIMIT 1")
    Performs findByCID(@Param("CID") String CID);

    List<Performs> findBySearch(@Param("params") Params params);
}
