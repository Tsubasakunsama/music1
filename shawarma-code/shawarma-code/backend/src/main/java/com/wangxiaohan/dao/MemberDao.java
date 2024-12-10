package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Member;
import com.wangxiaohan.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MemberDao extends Mapper<Member> {

    List<Member> findBySearch(@Param("params") Params params);

    @Select("SELECT * FROM member WHERE mname = #{mname} LIMIT 1")
    Member findByMname(@Param("mname") String mname);
}
