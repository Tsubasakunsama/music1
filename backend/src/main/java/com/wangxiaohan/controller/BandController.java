package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Band;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.BandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/band")
public class BandController {

    private static final Logger log = LoggerFactory.getLogger(BandController.class);

    @Resource
    private BandService bandService;

    @GetMapping
    public Result findAll() {
        List<Band> list = bandService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Band search query triggered");
        PageInfo<Band> info = bandService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Band band) {
        log.info("Received band data: {}", band);
        Band existingBand = bandService.findByBname(band.getBname());
        if (existingBand == null) {
            bandService.add(band);
            log.info("Band added: {}", band);
        } else {
            bandService.update(band);
            log.info("Band updated: {}", band);
        }
        return Result.success(band);
    }

    @DeleteMapping("/{bname}")
    public Result delete(@PathVariable String bname) {
        log.info("Deleting band with bname: {}", bname);
        bandService.delete(bname);
        return Result.success();
    }
}

