package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Album;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

@Repository
public interface AlbumDao extends Mapper<Album>{
    List<Album> findBySearch(@Param("params") Params params);

    @Select("SELECT * FROM album WHERE aname=#{aname} LIMIT 1")
    Album findByAname(@Param("aname") String aname);

}
