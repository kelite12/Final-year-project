package com.msit.ztkj.course.mapper;

import com.msit.ztkj.course.model.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 添加
    @Insert("insert into course(coursename,content,score,sendid,pic,status,sendtime,section,sectionspan,week,classroom,courseflag,teacherid,remark1,remark2)" +
    "values(#{coursename},#{content},#{score},#{sendid},#{pic},#{status},#{sendtime},#{section},#{sectionspan},#{week},#{classroom},#{courseflag},#{teacherid},#{remark1},#{remark2})")
    int addCourse(Course course);

    // 删除
    @Delete("delete from course where courseid=#{courseid}")
    int deleteCourse(Course course);

    // 查询所有
    @Select("select * from course")
    @Results({
            @Result(property = "sender",column = "sendid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
            @Result(property = "teacher",column = "teacherid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
    })
    List<Course> getAllCourse();

    //获取所有审核通过的课程
    @Select("select * from course where status=1")
    @Results({
            @Result(property = "sender",column = "sendid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
            @Result(property = "teacher",column = "teacherid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
    })
    List<Course> getAllOkCourse();

    // 查询某个
    @Select("select * from course where courseid=#{courseid}")
    Course getCourseById(int courseid);

    //通过星期查询课程
    @Select("select * from course where status=1 and week=#{week}")
    List<Course> getAllCourseByWeek(int week);

    // 修改状态
    @Update("update course set status=#{status} where courseid=#{courseid}")
    int updateCourseStatus(Course course);

    // 修改
    @Update({"<script>"
            + "update course "
            + "<trim prefix='set' suffixOverrides=','>"
            + "<if test='coursename!=null'>coursename=#{coursename},</if>"
            + "<if test='content!=null'>content=#{content},</if>"
            + "<if test='score!=null'>score=#{score},</if>"
            + "<if test='status!=null'>status=#{status},</if>"
            + "<if test='pic!=null'>pic=#{pic},</if>"
            + "<if test='sendtime!=null'>sendtime=#{sendtime},</if>"
            + "<if test='section!=null'>section=#{section},</if>"
            + "<if test='sectionspan!=null'>sectionspan=#{sectionspan},</if>"
            + "<if test='week!=null'>week=#{week},</if>"
            + "<if test='classroom!=null'>classroom=#{classroom},</if>"
            + "<if test='courseflag!=null'>courseflag=#{courseflag},</if>"
            + "<if test='teacherid!=null'>teacherid=#{teacherid},</if>"
            + "<if test='remark1!=null'>remark1=#{remark1},</if>"
            + "<if test='remark2!=null'>remark2=#{remark2},</if>"
            + "</trim>"
            + " where courseid=#{courseid} "
            + "</script>"})
    int updateCourse(Course course);
}
