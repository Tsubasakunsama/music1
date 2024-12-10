package com.wangxiaohan.dao;

import com.wangxiaohan.entity.Admin;
import com.wangxiaohan.entity.Params;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository //持久层,用来操作Admin
public interface AdminDao extends Mapper<Admin> {

    List<Admin> findBySearch(@Param("params") Params params);

    @Select("select * from admin where name=#{name} limit 1")
    Admin findByName(@Param("name") String name);

    @Select("select * from admin where name=#{name} and password=#{password} limit 1")
    Admin findByNameAndPassword(@Param("name")String name, @Param("password")String password);

    @Select("select * from admin where name=#{name} and phone=#{phone} limit 1")
    Admin findByNameAndPhone(@Param("name")String name, @Param("phone")String phone);


}
