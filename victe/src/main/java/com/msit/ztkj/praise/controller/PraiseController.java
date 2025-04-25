package com.msit.ztkj.praise.controller;

import com.msit.ztkj.praise.mapper.PraiseMapper;
import com.msit.ztkj.praise.model.Praise;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class PraiseController {

    @Resource
    PraiseMapper praiseMapper;

    @RequestMapping("/insertPraise")
    @ResponseBody
    public String insertPraise(Praise praise){
        return praiseMapper.insertPraise(praise)>0?"true":"false";
    }


    @RequestMapping("deletePraise")
    @ResponseBody
    public String deletePraise(Praise praise){
        return praiseMapper.deletePraise(praise)>0?"true":"false";
    }

    //取消点赞
    @RequestMapping("deleteMyPraise")
    @ResponseBody
    public String deleteMyPraise(Praise praise){
        return praiseMapper.deleteMyPraise(praise)>0?"true":"false";
    }

    @RequestMapping("getAllPraiseByTargetid")
    @ResponseBody
    public List<Praise> getAllPraiseByTargetid(Praise praise){
        return praiseMapper.getAllPraiseByTargetid(praise);
    }

    @RequestMapping("getAllPraiseByUserid")
    @ResponseBody
    public List<Praise> getAllPraiseByUserid(Praise praise){
        return praiseMapper.getAllPraiseByUserid(praise);
    }



}
