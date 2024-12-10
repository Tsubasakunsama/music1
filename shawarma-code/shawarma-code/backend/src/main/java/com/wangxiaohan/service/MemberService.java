package com.wangxiaohan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxiaohan.dao.MemberDao;
import com.wangxiaohan.entity.Member;
import com.wangxiaohan.entity.Params;
import com.wangxiaohan.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberService {

    @Resource
    private MemberDao memberDao;

    public List<Member> findAll() {
        return memberDao.selectAll();
    }

    public void add(Member member) {
        if (member.getMname() == null || "".equals(member.getMname())) {
            throw new CustomException("Member name cannot be empty");
        }

        Member existingMember = memberDao.findByMname(member.getMname());
        if (existingMember != null) {
            throw new CustomException("This member already exists");
        }

        memberDao.insertSelective(member);
    }

    public void update(Member member) {
        memberDao.updateByPrimaryKeySelective(member);
    }

    public PageInfo<Member> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Member> list = memberDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(String mname) {
        memberDao.deleteByPrimaryKey(mname);
    }
    public Member findByMname(String mname) {
        return memberDao.findByMname(mname);
    }
}
