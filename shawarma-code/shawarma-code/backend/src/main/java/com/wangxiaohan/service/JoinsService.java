package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.JoinsDao;
import com.wangxiaohan.entity.Joins;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JoinsService {

    @Resource
    private JoinsDao joinsDao;

    public List<Joins> findAll() {
        return joinsDao.selectAll();
    }

    public void add(Joins joins) {
        if (joins.getCID() == null || "".equals(joins.getCID())) {
            throw new CustomException("Concert ID cannot be empty");
        }

        Joins existingJoin = joinsDao.findByCID(joins.getCID());
        if (existingJoin != null) {
            throw new CustomException("This join already exists");
        }

        joinsDao.insertSelective(joins);
    }

    public void update(Joins joins) {
        joinsDao.updateByPrimaryKeySelective(joins);
    }

    public PageInfo<Joins> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Joins> list = joinsDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String CID) {
        joinsDao.deleteByPrimaryKey(CID);
    }

    public Joins findByCID(String CID) {
        return joinsDao.findByCID(CID);
    }
}
