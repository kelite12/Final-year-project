package com.msit.ztkj.course.controller;

import com.msit.ztkj.course.mapper.CourseMapper;
import com.msit.ztkj.course.model.Course;
import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Resource
    CourseMapper courseMapper;

    // 添加
    @ResponseBody
    @RequestMapping("/insertCourse")
    public String insertCourse(@RequestParam("picutre") MultipartFile[] pictures, Course course, HttpServletRequest request){
        try {
           // File path = new File(ResourceUtils.getURL("classpath:").getPath());
           // String savedDir = path.getAbsolutePath().replace("target\\classes","src\\main\\webapp\\file").toString();
            // System.out.println("+++++++++++++++++++++++"+savedDir);
            String pathStr = FileUtils.save(pictures);
            course.setPic(pathStr);
            if(course.getSendid()==0){
                User user = (User) request.getSession().getAttribute("user");
                course.setSendid(user.getUserid());
            }
            return courseMapper.addCourse(course)>0?"true":"false" ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "false";
    }

    // 删除
    @ResponseBody
    @RequestMapping("/deleteCourseByCourseid")
    public String deleteCourse(Course course) {return courseMapper.deleteCourse(course)>0?"true":"false";}

    // 查询所有
    @ResponseBody
    @RequestMapping("/getAllCourse")
    public List<Course> getAllCourse() {return courseMapper.getAllCourse();}

    // 查询所有通过审核的课程
    @ResponseBody
    @RequestMapping("/getAllOkCourse")
    public List<Course> getAllOkCourse() {return courseMapper.getAllOkCourse();}




    // 获取一周课程
    @ResponseBody
    @RequestMapping("/getAllWeekCourse")
    public List<List<Course>> getAllWeekCourse() {
        List<List<Course>> list = new ArrayList<List<Course>>();
        for(int i=1;i<=7;i++){
            List<Course> courses = new ArrayList<Course>();
            courses = courseMapper.getAllCourseByWeek(i);
            list.add(courses);
        }
        return list;
    }

    // 查询某个
    @ResponseBody
    @RequestMapping("/getCourseById")
    public Course getCourseById(Integer courseid) {return courseMapper.getCourseById(courseid);}

    // 修改状态
    @ResponseBody
    @RequestMapping("/updateCourseStatus")
    public String updateCourseStatus(Course course) {return courseMapper.updateCourseStatus(course)>0?"true":"flase";}

    // 修改
    @ResponseBody
    @RequestMapping("/updateCourse")
    public String updateCourse(Course course) {return courseMapper.updateCourse(course)>0?"true":"false";}
}
