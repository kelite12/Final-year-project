package com.msit.ztkj.fornum.controller;

import com.msit.ztkj.fornum.mapper.FornumMapper;
import com.msit.ztkj.fornum.model.Fornum;
import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
public class FornumController {

    @Resource
    FornumMapper fornumMapper;

    //获取所有
    @RequestMapping("/getAllFornum")
    @ResponseBody
    public List<Fornum> getAllFornum(){
        return fornumMapper.getAllFornum();
    }

    //增加
    @RequestMapping("/insertFornum")
    @ResponseBody
    public String insertFornum(@RequestParam("picture") MultipartFile[] pictures, Fornum fornum, HttpServletRequest request){
        System.out.println(fornum.getContent()+"---");
        try{
           // File path = new File(ResourceUtils.getURL("classpath:").getPath());
            //String savedDir =  path.getAbsolutePath().replace("target\\classes","src\\main\\webapp\\file").toString();
            String pathStr = FileUtils.save(pictures);
            if(fornum.getUserid()==0){
                User us = (User) request.getSession().getAttribute("user");
                fornum.setUserid(us.getUserid());
            }

            fornum.setUrls(pathStr);
            return fornumMapper.insertFornum(fornum)>0?"true":"false";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    //删除
    @RequestMapping("deleteFornum")
    @ResponseBody
    public String deleteFornum(Fornum fornum){
        return fornumMapper.deleteFornum(fornum)>0?"true":"false";
    }

    //获取我发布的
    @RequestMapping("/getAllMyFornum")
    @ResponseBody
    public List<Fornum> getAllMyFornum(Fornum fornum){
        return fornumMapper.getMyFornum(fornum);
    }

}
