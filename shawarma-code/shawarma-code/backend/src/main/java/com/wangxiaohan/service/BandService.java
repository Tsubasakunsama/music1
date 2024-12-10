package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.BandDao;
import com.wangxiaohan.entity.Band;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BandService {

    @Resource
    private BandDao bandDao;

    public List<Band> findAll() {
        return bandDao.selectAll();
    }

    public void add(Band band) {
        if (band.getBname() == null || "".equals(band.getBname())) {
            throw new CustomException("Band name cannot be empty");
        }
        Band existingBand = bandDao.findByBname(band.getBname());
        if (existingBand != null) {
            throw new CustomException("This band already exists");
        }

        bandDao.insertSelective(band);
    }

    public void update(Band band) {
        bandDao.updateByPrimaryKeySelective(band);
    }

    public PageInfo<Band> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Band> list = bandDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String bname) {
        bandDao.deleteByPrimaryKey(bname);
    }

    public Band findByBname(String bname) {
        return bandDao.findByBname(bname);
    }

}


