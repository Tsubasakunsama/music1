package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.AlbumDao;
import com.wangxiaohan.entity.Album;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.List;
@Service
public class AlbumService {

    @Resource
    private AlbumDao albumDao;

    public List<Album> findAll() {
        return albumDao.selectAll();
    }

    public void add(Album album) {
        if (album.getAname() == null || "".equals(album.getAname())) {
            throw new CustomException("Album name cannot be empty");
        }
        Album existingAlbum = albumDao.findByAname(album.getAname());
        if (existingAlbum != null) {
            throw new CustomException("This album already exists");
        }

        albumDao.insertSelective(album);
    }

    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    public PageInfo<Album> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Album> list = albumDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String aname) {
        albumDao.deleteByPrimaryKey(aname);
    }
    public Album findByAname(String aname) {
        return albumDao.findByAname(aname);
    }
}
