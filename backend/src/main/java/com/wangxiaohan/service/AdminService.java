package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.JwtTokenUtils;
import com.wangxiaohan.dao.AdminDao;
import com.wangxiaohan.entity.Admin;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll() {
        return adminDao.selectAll();
    }


    public PageInfo<Admin> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Admin> list=adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        //姓名非空
        if(admin.getName()==null||"".equals(admin.getName())){
            throw new CustomException("用户名不能为空");
        }
        //不允许重复新增
        Admin user=adminDao.findByName(admin.getName());
        //初始化一个密码
        if(user!=null){
            //提示前台
            throw new CustomException("该用户已存在");
        }
        if(admin.getPassword()==null){
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }


    public Admin login(Admin admin) {
        if(admin.getName()==null||"".equals(admin.getName())){
           throw new CustomException("用户名不能为空");
        }
        if(admin.getPassword()==null||"".equals(admin.getPassword())){
            throw new CustomException("密码不能为空");
        }
        Admin user=adminDao.findByNameAndPassword(admin.getName(),admin.getPassword());
         if (user==null){//没有该用户
             throw new CustomException("用户名或密码错误");
         }
         //生成登录用户对应的token,然后跟着user一起返回到前台
         String token= JwtTokenUtils.genToken(user.getId().toString(),user.getPassword());
         user.setToken(token);
         return user;
    }
    public Admin forget(Admin admin){
        if(admin.getName()==null||"".equals(admin.getName())){
            throw new CustomException("用户名不能为空");
        }
        if(admin.getPhone()==null||"".equals(admin.getPhone())){
            throw new CustomException("电话不能为空");
        }
        Admin user=adminDao.findByNameAndPhone(admin.getName(),admin.getPhone());
        if (user==null){//没有该用户
            throw new CustomException("查找不到该用户");
        }
        return user;
    }
    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }
}
