package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Concert;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.ConcertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/concert")
public class ConcertController {
    private static final Logger log = LoggerFactory.getLogger(ConcertController.class);
    @Resource
    private ConcertService concertService;

    @GetMapping
    public Result findAll() {
        List<Concert> list = concertService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Concert search query triggered");
        PageInfo<Concert> info = concertService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Concert concert) {
        Concert existingConcert = concertService.findByCID(concert.getCID());
        if (existingConcert == null) {
            concertService.add(concert);
            log.info("Concert added: {}", concert);
        } else {
            concertService.update(concert);
            log.info("Concert updated: {}", concert);
        }
        return Result.success(concert);
    }

    @DeleteMapping("/{CID}")
    public Result delete(@PathVariable String CID) {
        concertService.delete(CID);
        return Result.success();
    }
}
