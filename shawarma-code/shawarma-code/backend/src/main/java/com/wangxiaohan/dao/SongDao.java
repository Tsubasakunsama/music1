package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Song;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SongDao extends Mapper<Song> {

    List<Song> findBySearch(@Param("params") Params params);

    @Select("SELECT * FROM song WHERE sname=#{sname} LIMIT 1")
    Song findBySname(@Param("sname") String sname);
}
