package com.msit.ztkj.userinfo.mapper;

import com.msit.ztkj.userinfo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //添加用户
    @Insert("insert into user(username,nickname,password,sex,age,tel,userno,userLevel,address,headPic,remark1,remark2,height,weight) " +
            "values(#{username},#{nickname},#{password},#{sex},#{age},#{tel},#{userno},#{userLevel},#{address},#{headPic},#{remark1},#{remark2},#{height},#{weight})")
    int addUser(User user);

    //删除用户
    @Delete("delete from user where userid=#{userid}")
    int deleteUser(User user);

    //获取所有用户
    @Select("<script>" +
            "select * from user where 1=1 " +
            "<if test='username!=null'> and username like concat('%',#{username},'%')</if>" +
            " limit #{start},#{pageSize}" +
            "</script>")
    List<User> getAllUser(User user);

    @Select("<script>" +
            "select count(*) from user where 1=1 " +
            "<if test='username!=null'> and username like concat('%',#{username},'%')</if>" +
            "</script>")
    int totalUser(User user);

    //登录
    @Select("select * from user where username=#{username} and password=#{password}")
    User login(String username,String password);

    //通过id获取用户信息
    @Select("select * from user where userid=#{userid}")
    User getUser(User user);

    //获取某个等级的所有用户
    @Select("select * from user where userLevel=#{userLevel}")
    List<User> getAllUserByUserLevel(User user);


    @Select("select * from user")
    List<User> getAllUsers(User user);

    @Select("select * from user where username=#{username}")
    User getUserByUserName(User user);

    //修改用户信息
    @Update({"<script>"
            + "update user " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='nickname!=null'>nickname=#{nickname},</if>" +
            "<if test='password!=null'>password=#{password},</if>" +
            "<if test='sex!=null'>sex=#{sex},</if>" +
            "<if test='age!=0'>age=#{age},</if>" +
            "<if test='height!=0'>height=#{height},</if>" +
            "<if test='weight!=0'>weight=#{weight},</if>" +
            "<if test='tel!=null'>tel=#{tel},</if>" +
            "<if test='userno!=null'>userno=#{userno},</if>" +
//            "<if test='userLevel!=1'>userLevel=#{userLevel},</if>" +
            "<if test='address!=null'>address=#{address},</if>" +
            "<if test='headPic!=null'>headPic=#{headPic},</if>" +
            "<if test='remark1!=null'>remark1=#{remark1},</if>" +
            "<if test='remark2!=null'>remark2=#{remark2},</if>" +
            "</trim>"+
            "where userid=#{userid}"+
            "</script>"})
    int updateUser(User user);

}
