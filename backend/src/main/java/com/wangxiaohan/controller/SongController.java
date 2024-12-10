package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Song;
import com.wangxiaohan.entity.Song;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/song")
public class SongController {

    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    @Resource
    private SongService songService;

    @GetMapping
    public Result findAll() {
        List<Song> list = songService.findAll();
        return Result.success(list);
    }


    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Song search query triggered");
        PageInfo<Song> info = songService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Song song) {
        log.info("Received song data: {}", song);
        Song existingSong = songService.findBySname(song.getSname());
        if (existingSong == null) {
            songService.add(song);
            log.info("Song added: {}", song);
        } else {
            songService.update(song);
            log.info("Song updated: {}", song);
        }
        return Result.success(song);
    }


    @DeleteMapping("/{sname}")
    public Result delete(@PathVariable Integer id) {
        songService.delete(id);
        return Result.success();
    }
}
