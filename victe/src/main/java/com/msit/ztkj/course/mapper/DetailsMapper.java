package com.msit.ztkj.course.mapper;

import com.msit.ztkj.course.model.Details;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DetailsMapper {
    // 添加
    @Insert("insert into details(title,content,pic,video,courseid,status,remark1,remark2)" +
            "values(#{title},#{content},#{pic},#{video},#{courseid},#{status},#{remark1},#{remark2})")
    int addDetails(Details details);

    // 删除
    @Delete("delete from details where detailsid=#{detailsid}")
    int deleteDetails(int detailsid);

    // 查询所有
    @Select("select * from details")
    @Results({@Result(property = "course", column = "courseid", one = @One(select = "com.msit.ztkj.course.mapper.CourseMapper.getCourseById"))})
    List<Details> getAllDetails();

    // 查询某个
    @Select("select * from details where detailsid=#{detailsid}")
    @Results({@Result(property = "course", column = "courseid", one = @One(select = "com.msit.ztkj.course.mapper.CourseMapper.getCourseById"))})
    Details getDetailsById(int detailsid);

    // 通过courseid查询
    @Select("select * from details where courseid=#{courseid}")
    @Results({@Result(property = "course", column = "courseid", one = @One(select = "com.msit.ztkj.course.mapper.CourseMapper.getCourseById"))})
    List<Details> getDetailsByCourseid(int courseid);

    // 修改状态
    @Update("update details set status=#{status} where detailsid=#{detailsid}")
    int updateDetailsStatus(Details details);

    // 修改
    @Update({"<script>"
            + "update details "
            + "<trim prefix='set' suffixOverrides=','>"
            + "<if test='title!=null'>title=#{title},</if>"
            + "<if test='content!=null'>content=#{content},</if>"
            + "<if test='pic!=null'>pic=#{pic},</if>"
            + "<if test='video!=null'>video=#{video},</if>"
            + "<if test='courseid!=null'>courseid=#{courseid},</if>"
            + "<if test='status!=null'>status=#{status},</if>"
            + "<if test='remark1!=null'>remark1=#{remark1},</if>"
            + "<if test='remark2!=null'>remark2=#{remark2},</if>"
            + "</trim>"
            + " where detailsid=#{detailsid} "
            + "</script>"})
    int updateDetails(Details details);
}
