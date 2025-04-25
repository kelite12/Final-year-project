package com.msit.ztkj.friends.controller;
import com.msit.ztkj.friends.mapper.FriendsMapper;
import com.msit.ztkj.friends.model.Friends;
import com.msit.ztkj.userinfo.mapper.UserMapper;
import com.msit.ztkj.userinfo.model.User;
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
public class FriendsController {
    @Resource
    FriendsMapper friendsMapper;
    @Resource
    UserMapper userMapper;

    //获取所有
    @ResponseBody
    @RequestMapping("/getAllFriends")
    public List<Friends>getAllFriends(Friends friends){
        return friendsMapper.getAllFriends(friends);
    }

    @ResponseBody
    @RequestMapping("/getAllFriendsByUserId")
    public List<Friends>getAllFriendsByUserId(Friends friends){
        return friendsMapper.getAllFriendsByUserId(friends);
    }

    //获取所有
    @ResponseBody
    @RequestMapping("/getFriends")
    public Friends getFriends(Friends friends){return friendsMapper.getFriends(friends);}


    //删除
    @ResponseBody
    @RequestMapping("/deleteFriends")
    public String deleteFriends(Friends friends){
        return friendsMapper.deleteFriends(friends)>0?"true":"false";
    }


    //添加
    @ResponseBody
    @RequestMapping("/insertFriends")
    public String insertFriends(Friends friends){
        try{
            User user = new User();
            user.setUsername(friends.getUsername());
            User us = userMapper.getUserByUserName(user);
            if(us!=null && us.getUserid()>0){
                friends.setTouserid(us.getUserid());
                return friendsMapper.insertFriends(friends)>0?"true":"false";
            }

        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";

    }

    //修改
    @ResponseBody
    @RequestMapping("/updateFriends")
    public String updateFriends(@RequestParam("picture") MultipartFile[] pictures,Friends friends){
        try{
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                
            }else{
                
            }
            return friendsMapper.updateFriends(friends)>0?"true":"false";
        }catch(Exception e){
             e.printStackTrace();
        }
        return "false";
    }

    //分页
    @ResponseBody
    @RequestMapping("/getAllFriendsByPage")
    public Page getAllFriendsByPage(Friends friends){
        if(friends.getPageNo()==0){
            friends.setPageNo(1);
        }
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(friends.getPageNo(),pageSize);
        //设置查询
        friends.setStart((friends.getPageNo()-1));
        friends.setPageSize(10);
        page.setResult(friendsMapper.getAllFriendsByPage(friends),friendsMapper.totalFriends(friends));
        return page;
    }
}