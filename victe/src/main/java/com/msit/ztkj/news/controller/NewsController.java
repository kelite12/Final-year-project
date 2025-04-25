package com.msit.ztkj.news.controller;

import com.msit.ztkj.news.mapper.NewsMapper;
import com.msit.ztkj.news.model.News;
import com.msit.ztkj.utils.FileUtils;
import com.msit.ztkj.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class NewsController {
    @Resource
    NewsMapper newsMapper;

    //获取所有
    @ResponseBody
    @RequestMapping("/getAllNews")
    public List<News>getAllNews(News news){return newsMapper.getAllNews(news);}

    //获取所有
    @ResponseBody
    @RequestMapping("/getNews")
    public News getNews(News news){return newsMapper.getNews(news);}


    //删除
    @ResponseBody
    @RequestMapping("/deleteNews")
    public String deleteNews(News news){
        return newsMapper.deleteNews(news)>0?"true":"false";
    }


    //添加
    @ResponseBody
    @RequestMapping("/insertNews")
    public String insertNews(@RequestParam("picture") MultipartFile[] pictures,News news){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                 
            }else{
                
            }
            return newsMapper.insertNews(news)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";

    }

    //修改
    @ResponseBody
    @RequestMapping("/updateNews")
    public String updateNews(@RequestParam("picture") MultipartFile[] pictures,News news){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                
            }else{
                
            }
            return newsMapper.updateNews(news)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";
    }

    //分页
    @ResponseBody
    @RequestMapping("/getAllNewsByPage")
    public Page getAllNewsByPage(News news){
        if(news.getPageNo()==0){
            news.setPageNo(1);
        }
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(news.getPageNo(),pageSize);
        //设置查询
        news.setStart((news.getPageNo()-1)*pageSize);
        news.setPageSize(pageSize);
        page.setResult(newsMapper.getAllNewsByPage(news),newsMapper.totalNews(news));
        return page;
    }
}