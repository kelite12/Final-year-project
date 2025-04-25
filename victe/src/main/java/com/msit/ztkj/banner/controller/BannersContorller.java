package com.msit.ztkj.banner.controller;

import com.msit.ztkj.banner.mapper.BannersMapper;
import com.msit.ztkj.banner.model.Banners;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class BannersContorller {
    @Resource
    BannersMapper bannersMapper;

    // 添加banner
    @ResponseBody
    @RequestMapping("/insertBanners")
    public String insertBanners(@RequestParam("picutre") MultipartFile[] pictures, Banners banners){
        try {
           // File path = new File(ResourceUtils.getURL("classpath:").getPath());
            //String savedDir = path.getAbsolutePath().replace("target\\classes","src\\main\\webapp\\file").toString();
           // System.out.println("+++++++++++++++++++++++"+savedDir);
            String pathStr = FileUtils.save(pictures);
            banners.setPic(pathStr);
            return bannersMapper.addBanners(banners)>0?"true":"false" ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "false";
    }

    // 删除banner
    @ResponseBody
    @RequestMapping("/deleteBannersByBannersid")
    public String deleteBanners(Banners banners) {return bannersMapper.deleteBanners(banners)>0?"true":"false";}

    // 查询所有banner
    @ResponseBody
    @RequestMapping("/getAllBanners")
    public List<Banners> getAllBanners() {return bannersMapper.getAllBanners();}

    // 询能使用banner
    @ResponseBody
    @RequestMapping("/getUsedBanners")
    public List<Banners> getUsedBanners() {return bannersMapper.getUsedBanners();}

    // 查询某个banner
    @ResponseBody
    @RequestMapping("/getBannersById")
    public Banners getBannersById(int bannersid) {return bannersMapper.getBannersById(bannersid);}

    // 修改banner
    @ResponseBody
    @RequestMapping("/updateBanners")
    public String updateBanners(Banners banners) {return bannersMapper.updateBanners(banners)>0?"true":"false";}
}
