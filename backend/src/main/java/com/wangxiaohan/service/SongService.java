package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.SongDao;
import com.wangxiaohan.entity.Song;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongService {

    @Resource
    private SongDao songDao;

    public List<Song> findAll() {
        return songDao.selectAll();
    }

    public void add(Song song) {
        if (song.getSname() == null || "".equals(song.getSname())) {
            throw new CustomException("Song name cannot be empty");
        }

        Song existingSong = songDao.findBySname(song.getSname());
        if (existingSong != null) {
            throw new CustomException("This song already exists");
        }

        songDao.insertSelective(song);
    }

    public void update(Song song) {
        songDao.updateByPrimaryKeySelective(song);
    }

    public PageInfo<Song> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Song> list = songDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(Integer id) {
        songDao.deleteByPrimaryKey(id);
    }

    public Song findBySname(String sname) {
        return songDao.findBySname(sname);
    }
}
