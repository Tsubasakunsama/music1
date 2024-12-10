package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Album;
import com.wangxiaohan.entity.Album;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Resource
    private AlbumService albumService;

    @GetMapping
    public Result findAll() {
        List<Album> list = albumService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Album search query triggered");
        PageInfo<Album> info = albumService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Album album) {
        log.info("Received album data: {}", album);
        Album existingAlbum = albumService.findByAname(album.getAname());
        if (existingAlbum == null) {
            albumService.add(album);
            log.info("Album added: {}", album);
        } else {
            albumService.update(album);
            log.info("Album updated: {}", album);
        }
        return Result.success(album);
    }

    @DeleteMapping("/{aname}")
    public Result delete(@PathVariable String aname) {
        log.info("Deleting album with aname: {}", aname);
        albumService.delete(aname);
        return Result.success();
    }
}
