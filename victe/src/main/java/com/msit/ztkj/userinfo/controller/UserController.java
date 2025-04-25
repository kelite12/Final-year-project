package com.msit.ztkj.userinfo.controller;

import com.msit.ztkj.userinfo.mapper.UserMapper;
import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.FileUtils;
import com.msit.ztkj.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Resource
    UserMapper userMapper;


    //获取所有用户
    @ResponseBody
    @RequestMapping("/getAllUser")
    public Page getAllUser(User user){
        int pageSize = 10;//每页显示的记录数
        Page page = new Page(user.getPageNo(),pageSize);
        //设置查询
        user.setStart((user.getPageNo()-1)*pageSize);
        user.setPageSize(pageSize);
        page.setResult(userMapper.getAllUser(user),userMapper.totalUser(user));
        return page;
    }

    //获取所有某个等级用户
    @ResponseBody
    @RequestMapping("/getAllUserByUserLevel")
    public List<User> getAllUserByUserLevel(User user){
        return userMapper.getAllUserByUserLevel(user);
    }

    @ResponseBody
    @RequestMapping("/getAllUsers")
    public List<User> getAllUsers(User user){
        return userMapper.getAllUsers(user);
    }

    //删除用户
    @ResponseBody
    @RequestMapping("/deleteUserByUserid")
    public String deleteUser(User user){
        return userMapper.deleteUser(user)>0?"true":"false";
    }

    //修改用户
    @ResponseBody
    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("pic") MultipartFile[] pictures,User user){
        try {
            if(pictures!=null && pictures.length>0){
                String pathStr = FileUtils.save(pictures);
                user.setHeadPic(pathStr);
            }
            return userMapper.updateUser(user)>0?"true":"false";
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        return "false";
    }
    //修改用户
    @ResponseBody
    @RequestMapping("/updateUser2")
    public String updateUser2(User user){
        return userMapper.updateUser(user)>0?"true":"false";
    }
    //登录
    @ResponseBody
    @RequestMapping("/login")
    public User login(String username,String password,HttpServletRequest request){
        User user = userMapper.login(username,password);
        request.getSession().setAttribute("user",user);
        return user;
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser(User user){
        return userMapper.getUser(user);
    }


    //获取session
    @ResponseBody
    @RequestMapping("/getSession")
    public User getSession(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    //添加用户
    @ResponseBody
    @RequestMapping("/insertUser")
    public String insertUser(@RequestParam("pic") MultipartFile[] pictures,User user){
        try {
            String pathStr = FileUtils.save(pictures);
            user.setHeadPic(pathStr);
            return userMapper.addUser(user)>0?"true":"false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }


}
