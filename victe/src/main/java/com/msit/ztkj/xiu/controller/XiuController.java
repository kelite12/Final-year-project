package com.msit.ztkj.xiu.controller;
import com.msit.ztkj.xiu.mapper.XiuMapper;
import com.msit.ztkj.xiu.model.Xiu;
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
public class XiuController {
    @Resource
    XiuMapper xiuMapper;

    //获取所有
    @ResponseBody
    @RequestMapping("/getAllXiu")
    public List<Xiu>getAllXiu(Xiu xiu){return xiuMapper.getAllXiu(xiu);}

    //获取所有
    @ResponseBody
    @RequestMapping("/getXiu")
    public Xiu getXiu(Xiu xiu){return xiuMapper.getXiu(xiu);}


    //删除
    @ResponseBody
    @RequestMapping("/deleteXiu")
    public String deleteXiu(Xiu xiu){
        return xiuMapper.deleteXiu(xiu)>0?"true":"false";
    }


    //添加
    @ResponseBody
    @RequestMapping("/insertXiu")
    public String insertXiu(@RequestParam("picture") MultipartFile[] pictures,Xiu xiu){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                 
            }else{
                
            }
            return xiuMapper.insertXiu(xiu)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";

    }

    //修改
    @ResponseBody
    @RequestMapping("/updateXiu")
    public String updateXiu(@RequestParam("picture") MultipartFile[] pictures,Xiu xiu){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                
            }else{
                
            }
            return xiuMapper.updateXiu(xiu)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";
    }

    //分页
    @ResponseBody
    @RequestMapping("/getAllXiuByPage")
    public Page getAllXiuByPage(Xiu xiu){
        if(xiu.getPageNo()==0){
            xiu.setPageNo(1);
        }
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(xiu.getPageNo(),pageSize);
        //设置查询
        xiu.setStart((xiu.getPageNo()-1)*pageSize);
        xiu.setPageSize(pageSize);
        page.setResult(xiuMapper.getAllXiuByPage(xiu),xiuMapper.totalXiu(xiu));
        return page;
    }
}