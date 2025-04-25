package com.msit.ztkj.course.controller;

import com.msit.ztkj.course.mapper.DetailsMapper;
import com.msit.ztkj.course.model.Details;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DetailsController {
    @Resource
    DetailsMapper detailsMapper;

    // 添加
    @ResponseBody
    @RequestMapping("/insertDetails")
    public String insertDeatils(@RequestParam("picutre")MultipartFile[] picutres,@RequestParam("videoFres")MultipartFile[] videoFres, Details details) {
        try {
           // File path = new File((ResourceUtils.getURL("classpath:").getPath()));
            //String saveDir = path.getAbsolutePath().replace("target\\classes","src\\main\\webapp\\file").toString();
            String picPath = FileUtils.save(picutres);
            String videoPath = FileUtils.save(videoFres);
            details.setPic(picPath);
            details.setVideo(videoPath);
            return detailsMapper.addDetails(details)>0?"true":"false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    // 删除
    @ResponseBody
    @RequestMapping("/deleteDetails")
    public String deleteDetails(int detailsid) { return detailsMapper.deleteDetails(detailsid) > 0 ? "true" : "false"; }

    // 查询所有
    @ResponseBody
    @RequestMapping("/getAllDetails")
    public List<Details> getAllDetails() { return detailsMapper.getAllDetails(); }

    // 查询某个
    @ResponseBody
    @RequestMapping("/getDetailsById")
    public Details getDetailsById(int detailsid) {return detailsMapper.getDetailsById(detailsid);}

    // 通过courseid查询
    @ResponseBody
    @RequestMapping("/getDetailsByCourseid")
    public List<Details> getDetailsByCourseid(int courseid) {return detailsMapper.getDetailsByCourseid(courseid);}

    // 修改状态
    @ResponseBody
    @RequestMapping("/updateDetailsStatus")
    public String updateDetailsStatus(Details details) {return detailsMapper.updateDetailsStatus(details)>0?"true":"false";}

    // 修改
    @ResponseBody
    @RequestMapping("/updateDetails")
    public String updateDetails(Details details) {return detailsMapper.updateDetails(details)>0?"true":"false";}
}
