package com.msit.ztkj.health.controller;
import com.msit.ztkj.health.mapper.HealthMapper;
import com.msit.ztkj.health.model.Health;
import com.msit.ztkj.utils.Page;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HealthController {
    @Resource
    HealthMapper healthMapper;

    //获取所有
    @ResponseBody
    @RequestMapping("/getAllHealth")
    public List<Health>getAllHealth(Health health){
        return healthMapper.getAllHealth(health);
    }

    //获取所有
    @ResponseBody
    @RequestMapping("/getHealth")
    public Health getHealth(Health health){return healthMapper.getHealth(health);}


    //删除
    @ResponseBody
    @RequestMapping("/deleteHealth")
    public String deleteHealth(Health health){
        return healthMapper.deleteHealth(health)>0?"true":"false";
    }


    //添加
    @ResponseBody
    @RequestMapping("/insertHealth")
    public String insertHealth(@RequestParam("picture") MultipartFile[] pictures,Health health){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                 
            }else{
                
            }
            return healthMapper.insertHealth(health)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";

    }

    //修改
    @ResponseBody
    @RequestMapping("/updateHealth")
    public String updateHealth(@RequestParam("picture") MultipartFile[] pictures,Health health){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                
            }else{
                
            }
            return healthMapper.updateHealth(health)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";
    }

    //分页
    @ResponseBody
    @RequestMapping("/getAllHealthByPage")
    public Page getAllHealthByPage(Health health){
        if(health.getPageNo()==0){
            health.setPageNo(1);
        }
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(health.getPageNo(),pageSize);
        //设置查询
        health.setStart((health.getPageNo()-1)*pageSize);
        health.setPageSize(pageSize);
        page.setResult(healthMapper.getAllHealthByPage(health),healthMapper.totalHealth(health));
        return page;
    }
}