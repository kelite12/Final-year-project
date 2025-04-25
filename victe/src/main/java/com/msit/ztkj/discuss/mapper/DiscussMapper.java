package com.msit.ztkj.discuss.mapper;

import com.msit.ztkj.discuss.model.Discuss;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface DiscussMapper {
    //添加评论
    @Insert("insert into discuss(content,userid,status,remark1,targetid) " +
            "values(#{content},#{userid},#{status},#{remark1},#{targetid})")
    int addDiscuss(Discuss discuss);

    //删除评论
    @Delete("delete from discuss where discussid=#{discussid}")
    int deleteDiscuss(Discuss discuss);

    //获取所有评论
    @Select("select * from discuss")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Discuss> getAllDiscuss();

    //获取所有评论
    @Select("select * from discuss where targetid=#{targetid}")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Discuss> getAllDiscussByTargetId(int targetid);

    //获取所有通过审核的评论
    @Select("select * from Discuss where status=0")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Discuss> getAllDiscussByStatus();


    //修改评论信息
    @Update({"<script>"
            + "update discuss " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='status!=-1'>status=#{status},</if>" +
            "<if test='remark1!=null'>remark1=#{remark1},</if>" +
            "</trim>"+
            "where discussid=#{discussid}"+
            "</script>"})
    int updateDiscuss(Discuss discuss);
}
