package com.msit.ztkj.yin.controller;
import com.msit.ztkj.yin.mapper.YinMapper;
import com.msit.ztkj.yin.model.Yin;
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
public class YinController {
    @Resource
    YinMapper yinMapper;

    //获取所有
    @ResponseBody
    @RequestMapping("/getAllYin")
    public List<Yin>getAllYin(Yin yin){return yinMapper.getAllYin(yin);}

    @ResponseBody
    @RequestMapping("/getAllYin2")
    public List<Yin>getAllYin2(Yin yin){return yinMapper.getAllYin(yin);}

    //获取所有
    @ResponseBody
    @RequestMapping("/getYin")
    public Yin getYin(Yin yin){return yinMapper.getYin(yin);}


    //删除
    @ResponseBody
    @RequestMapping("/deleteYin")
    public String deleteYin(Yin yin){
        return yinMapper.deleteYin(yin)>0?"true":"false";
    }


    //添加
    @ResponseBody
    @RequestMapping("/insertYin")
    public String insertYin(@RequestParam("picture_pics") MultipartFile[] picture_picss,Yin yin){
        try{
            if(picture_picss!=null && picture_picss.length>0){
                String pathStr_pics = FileUtils.save(picture_picss);
                yin.setPics(pathStr_pics);
            }else{
                     yin.setPics(null);
            }return yinMapper.insertYin(yin)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";

    }

    //修改
    @ResponseBody
    @RequestMapping("/updateYin")
    public String updateYin(@RequestParam("picture_pics") MultipartFile[] picture_picss,Yin yin){
        try{
            if(picture_picss!=null && picture_picss.length>0){
                String pathStr_pics = FileUtils.save(picture_picss);

                yin.setPics(pathStr_pics);


            }else{

                     yin.setPics(null);

            }return yinMapper.updateYin(yin)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";
    }

    //分页
    @ResponseBody
    @RequestMapping("/getAllYinByPage")
    public Page getAllYinByPage(Yin yin){
        if(yin.getPageNo()==0){
            yin.setPageNo(1);
        }
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(yin.getPageNo(),pageSize);
        //设置查询
        yin.setStart((yin.getPageNo()-1)*pageSize);
        yin.setPageSize(pageSize);
        page.setResult(yinMapper.getAllYinByPage(yin),yinMapper.totalYin(yin));
        return page;
    }
}