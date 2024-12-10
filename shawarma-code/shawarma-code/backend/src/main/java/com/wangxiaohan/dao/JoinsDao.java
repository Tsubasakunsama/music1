package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Joins;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface JoinsDao extends Mapper<Joins> {

    @Select("SELECT * FROM joins WHERE CID = #{CID} LIMIT 1")
    Joins findByCID(@Param("CID") String CID);

    List<Joins> findBySearch(@Param("params") Params params);
}
