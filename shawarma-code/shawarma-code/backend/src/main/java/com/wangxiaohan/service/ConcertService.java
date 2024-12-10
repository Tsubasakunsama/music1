package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.ConcertDao;
import com.wangxiaohan.entity.Concert;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConcertService {

    @Resource
    private ConcertDao concertDao;

    public List<Concert> findAll() {
        return concertDao.selectAll();
    }

    public void add(Concert concert) {
        if (concert.getCID() == null || "".equals(concert.getCID())) {
            throw new CustomException("Concert ID cannot be empty");
        }

        Concert existingConcert = concertDao.findByCID(concert.getCID());
        if (existingConcert != null) {
            throw new CustomException("This concert already exists");
        }

        concertDao.insertSelective(concert);
    }

    public void update(Concert concert) {
        concertDao.updateByPrimaryKeySelective(concert);
    }

    public PageInfo<Concert> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Concert> list = concertDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String CID) {
        concertDao.deleteByPrimaryKey(CID);
    }

    public Concert findByCID(String CID) {
        return concertDao.findByCID(CID);
    }
}
