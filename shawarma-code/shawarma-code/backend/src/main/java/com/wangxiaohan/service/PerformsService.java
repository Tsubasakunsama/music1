package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.PerformsDao;
import com.wangxiaohan.entity.Performs;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PerformsService {

    @Resource
    private PerformsDao performsDao;

    public List<Performs> findAll() {
        return performsDao.selectAll();
    }

    public void add(Performs performs) {
        if (performs.getCID() == null || "".equals(performs.getCID())) {
            throw new CustomException("Concert ID cannot be empty");
        }

        Performs existingJoin = performsDao.findByCID(performs.getCID());
        if (existingJoin != null) {
            throw new CustomException("This join already exists");
        }

        performsDao.insertSelective(performs);
    }

    public void update(Performs performs) {
        performsDao.updateByPrimaryKeySelective(performs);
    }

    public PageInfo<Performs> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Performs> list = performsDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String CID) {
        performsDao.deleteByPrimaryKey(CID);
    }

    public Performs findByCID(String CID) {
        return performsDao.findByCID(CID);
    }
}
