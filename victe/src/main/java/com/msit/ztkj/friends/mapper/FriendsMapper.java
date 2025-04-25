package com.msit.ztkj.friends.mapper;

import com.msit.ztkj.friends.model.Friends;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FriendsMapper {

    //添加
    @Insert("insert into friends(friendsid,senduserid,touserid,status,remark1) " +
            "values(#{friendsid},#{senduserid},#{touserid},#{status},#{remark1})")
    int insertFriends(Friends friends);

    //删除
    @Delete("delete from friends where friendsid=#{ friendsid }")
    int deleteFriends(Friends friends);

    //获取所有
    @Select("select * from friends")
    List<Friends> getAllFriends(Friends friends);

    @Select("select * from friends where senduserid=#{senduserid} or  touserid=#{senduserid}")
    @Results({
            @Result(property = "user",column = "touserid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
            @Result(property = "user2",column = "senduserid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
            @Result(property = "touserid",column = "touserid")})

    List<Friends> getAllFriendsByUserId(Friends friends);


     //修改用户信息
    @Update({"<script>"+
            "update friends " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='friendsid!=null'>friendsid=#{friendsid},</if><if test='senduserid!=null'>senduserid=#{senduserid},</if><if test='touserid!=null'>touserid=#{touserid},</if><if test='status!=null'>status=#{status},</if><if test='remark1!=null'>remark1=#{remark1},</if>"+
            "</trim>"+
            "where friendsid=#{ friendsid }"+
            "</script>"})
    int updateFriends(Friends friends);


    //通过id获取信息
    @Select("select * from friends where friendsid=#{ friendsid }")
    Friends getFriends(Friends friends);

   @Select("<script>" +
            "select * from friends where 1=1 " +
            ""+
            " limit #{start},#{pageSize}" +
            "</script>")
    List<Friends> getAllFriendsByPage(Friends friends);

   //统计记录数
   @Select("<script>" +
            "select count(*) from friends where 1=1 " +
            ""+
            "</script>")
    int totalFriends(Friends friends);
}