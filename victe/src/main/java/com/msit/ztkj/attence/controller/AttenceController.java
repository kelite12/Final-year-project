package com.msit.ztkj.attence.controller;

import com.msit.ztkj.attence.mapper.AttenceMapper;
import com.msit.ztkj.attence.model.Attence;
import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class AttenceController {
    @Resource
    AttenceMapper attenceMapper;

    @RequestMapping("/getAllAttence")
    @ResponseBody
    public List<Attence> getAllAttence(){
        return attenceMapper.getAllAttence();
    }

    @RequestMapping("/getAllAttence2")
    @ResponseBody
    public List<Attence> getAllAttence2(){
        return attenceMapper.getAllAttence2();
    }

    @RequestMapping("/getAllAttence3")
    @ResponseBody
    public List<Attence> getAllAttence3(User user){
        return attenceMapper.getAllAttence3(user);
    }

    @RequestMapping("/getMyAttence")
    @ResponseBody
    public List<Attence> getMyAttence(Attence attence){
        return attenceMapper.getAllMyAttence(attence);
    }

    @RequestMapping("/getAllByRemark")
    @ResponseBody
    public List<Attence> getAllByRemark(Attence attence){

        return attenceMapper.getAllByRemark(attence);
    }

//    @RequestMapping("/insertAttence")
//    @ResponseBody
//    public String insertAttence(Attence attence){
//        List<Attence> ae = attenceMapper.checkAttence(attence);
//        if(ae!=null && !ae.isEmpty()) {
//            return "-1";
//        }
//        return attenceMapper.insert(attence)>0?"true":"false";
//    }

    @RequestMapping("/insertAttence")
    @ResponseBody
    public String insertAttence(@RequestParam("picture") MultipartFile[] pictures, Attence attence){
        List<Attence> ae = attenceMapper.checkAttence(attence);
        if(ae!=null && !ae.isEmpty()) {
            return "-1";
        }
        try{
            if(pictures!=null){
                String pathStr = FileUtils.save(pictures);
                attence.setRemark1(pathStr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return attenceMapper.insert(attence)>0?"true":"false";
    }

    @RequestMapping("/deleteAttence")
    @ResponseBody
    public String deleteAttence(Attence attence){
        return attenceMapper.delete(attence)>0?"true":"false";
    }


}
