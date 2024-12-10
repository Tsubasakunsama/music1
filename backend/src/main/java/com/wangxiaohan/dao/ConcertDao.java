package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Concert;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ConcertDao extends Mapper<Concert> {
    List<Concert> findBySearch(@Param("params") Params params);
    @Select("SELECT * FROM concert WHERE CID = #{CID} LIMIT 1")
    Concert findByCID(@Param("CID") String CID);
}
