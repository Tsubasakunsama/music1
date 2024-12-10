package com.wangxiaohan.controller;

import com.github.pagehelper.PageInfo;
import com.wangxiaohan.common.Result;
import com.wangxiaohan.entity.Member;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberService memberService;

    @GetMapping
    public Result findAll() {
        List<Member> list = memberService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("Member search query triggered");
        PageInfo<Member> info = memberService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Member member) {
        log.info("Received Member data: {}", member);
        Member existingMember = memberService.findByMname(member.getMname());
        if (existingMember == null) {
            memberService.add(member);
            log.info("Member added: {}", member);
        } else {
            memberService.update(member);
            log.info("Member updated: {}", member);
        }
        return Result.success(member);
    }

    @DeleteMapping("/{mname}")
    public Result delete(@PathVariable String mname) {
        memberService.delete(mname);
        return Result.success();
    }
}
