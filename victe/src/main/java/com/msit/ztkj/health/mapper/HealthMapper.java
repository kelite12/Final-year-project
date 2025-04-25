package com.msit.ztkj.health.mapper;

import com.msit.ztkj.health.model.Health;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface HealthMapper {

    //添加
    @Insert("insert into health(healthid,age,sex,xuey,xuet,xuez,xueg,userid,remark1) " +
            "values(#{healthid},#{age},#{sex},#{xuey},#{xuet},#{xuez},#{xueg},#{userid},#{remark1})")
    int insertHealth(Health health);

    //删除
    @Delete("delete from health where healthid=#{ healthid }")
    int deleteHealth(Health health);

    //获取所有
    @Select("select * from health where userid=#{userid}")
    @Results({
            @Result(column = "userid",property = "userid"),
            @Result(column = "userid",property = "user",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))
    })
    List<Health> getAllHealth(Health health);


     //修改用户信息
    @Update({"<script>"+
            "update health " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='healthid!=null'>healthid=#{healthid},</if><if test='age!=null'>age=#{age},</if><if test='sex!=null'>sex=#{sex},</if><if test='xuey!=null'>xuey=#{xuey},</if><if test='xuet!=null'>xuet=#{xuet},</if><if test='xuez!=null'>xuez=#{xuez},</if><if test='xueg!=null'>xueg=#{xueg},</if><if test='userid!=null'>userid=#{userid},</if><if test='remark1!=null'>remark1=#{remark1},</if>"+
            "</trim>"+
            "where healthid=#{ healthid }"+
            "</script>"})
    int updateHealth(Health health);


    //通过id获取信息
    @Select("select * from health where healthid=#{ healthid }")
    @Results({
            @Result(column = "userid",property = "userid"),
            @Result(column = "userid",property = "user",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))
    })
    Health getHealth(Health health);

   @Select("<script>" +
            "select * from health where 1=1 " +
            "<if test='userid!=null'> and userid like concat('%',#{userid},'%')</if>"+
            " limit #{start},#{pageSize}" +
            "</script>")
   @Results({
           @Result(column = "userid",property = "userid"),
           @Result(column = "userid",property = "user",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))
   })
    List<Health> getAllHealthByPage(Health health);

   //统计记录数
   @Select("<script>" +
            "select count(*) from health where 1=1 " +
            "<if test='userid!=null'> and userid like concat('%',#{userid},'%')</if>"+
            "</script>")
    int totalHealth(Health health);
}