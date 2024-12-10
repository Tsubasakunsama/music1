package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Performs;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.PerformsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/performs")
public class PerformsController {

    private static final Logger log = LoggerFactory.getLogger(PerformsController.class);

    @Resource
    private PerformsService performsService;

    @GetMapping
    public Result findAll() {
        List<Performs> list = performsService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Performs search query triggered");
        PageInfo<Performs> info = performsService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Performs performs) {
        Performs existingJoin = performsService.findByCID(performs.getCID());
        if (existingJoin == null) {
            performsService.add(performs);
            log.info("Performs added: {}", performs);
        } else {
            performsService.update(performs);
            log.info("Performs updated: {}", performs);
        }
        return Result.success(performs);
    }

    @DeleteMapping("/{CID}")
    public Result delete(@PathVariable String CID) {
        performsService.delete(CID);
        return Result.success();
    }
}
