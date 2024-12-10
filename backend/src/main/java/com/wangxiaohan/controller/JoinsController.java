package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Joins;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.JoinsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/joins")
public class JoinsController {

    private static final Logger log = LoggerFactory.getLogger(JoinsController.class);

    @Resource
    private JoinsService joinsService;

    @GetMapping
    public Result findAll() {
        List<Joins> list = joinsService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Joins search query triggered");
        PageInfo<Joins> info = joinsService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Joins joins) {
        Joins existingJoin = joinsService.findByCID(joins.getCID());
        if (existingJoin == null) {
            joinsService.add(joins);
            log.info("Joins added: {}", joins);
        } else {
            joinsService.update(joins);
            log.info("Joins updated: {}", joins);
        }
        return Result.success(joins);
    }

    @DeleteMapping("/{CID}")
    public Result delete(@PathVariable String CID) {
        joinsService.delete(CID);
        return Result.success();
    }
}
