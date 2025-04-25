package com.msit.ztkj.discuss.controller;

import com.msit.ztkj.discuss.mapper.DiscussMapper;
import com.msit.ztkj.discuss.model.Discuss;
import com.msit.ztkj.news.mapper.NewsMapper;
import com.msit.ztkj.news.model.News;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DiscussController {
    @Resource
    DiscussMapper discussMapper;
    //获取所有评论
    @ResponseBody
    @RequestMapping("/getAllDiscuss")
    public List<Discuss> getAllDiscuss(){return discussMapper.getAllDiscuss();}


    //获取所有通过审核的评论
    @ResponseBody
    @RequestMapping("/getAllDiscussByStatus")
    public List<Discuss> getAllDiscussByStatus(){return discussMapper.getAllDiscussByStatus();}

    //获取所有的评论通过targetid
    @ResponseBody
    @RequestMapping("/getAllDiscussByTargetId")
    public List<Discuss> getAllDiscussByTargetId(int targetid){return discussMapper.getAllDiscussByTargetId(targetid);}

    //删除评论
    @ResponseBody
    @RequestMapping("/deleteDiscussByDiscussid")
    public String deleteDiscuss(Discuss discuss){
        return discussMapper.deleteDiscuss(discuss)>0?"true":"false";
    }


    //添加评论
    @ResponseBody
    @RequestMapping("/insertDiscuss")
    public String insertNews(Discuss discuss){
        return discussMapper.addDiscuss(discuss)>0?"true":"false";
    }

    //修改评论
    @ResponseBody
    @RequestMapping("/updateDiscuss")
    public String updateDiscuss(Discuss discuss){
        return discussMapper.updateDiscuss(discuss)>0?"true":"false";
    }
}
