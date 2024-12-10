package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Admin;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        Admin loginUser=adminService.login(admin);
        return Result.success(loginUser);

    }
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();

    }
    @PostMapping("/forget")
    public Result forget(@RequestBody Admin admin){
        Admin loginUser=adminService.forget(admin);
        return Result.success(loginUser);
    }

    @PostMapping
    public Result save(@RequestBody Admin admin){
        if(admin.getId()==null){
            adminService.add(admin);
        }else{
            adminService.update(admin);
        }
        return Result.success(admin);
    }

    @GetMapping
    public Result findAll(){
        List<Admin> list= adminService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("拦截器已放行");
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }
    @DeleteMapping("/{aname}")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }
}
