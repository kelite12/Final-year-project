package com.msit.ztkj.fornum.mapper;

import com.msit.ztkj.fornum.model.Fornum;
import com.msit.ztkj.praise.model.Praise;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FornumMapper {

    @Insert("insert into fornum(title,content,urls,userid,status,remark1) values(#{title},#{content},#{urls},#{userid},#{status},#{remark1})")
    int insertFornum(Fornum fornum);


    @Delete("delete from fornum where fornumid=#{fornumid}")
    int deleteFornum(Fornum fornum);

    @Select("select * from fornum where status=0 order by sendtime desc")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser")),
              @Result(property = "fornumid",column = "fornumid"),
              @Result(property = "userid",column = "userid"),
              @Result(property = "discusses",column ="fornumid",many = @Many(select ="com.msit.ztkj.discuss.mapper.DiscussMapper.getAllDiscussByTargetId")),
            @Result(property = "praises",column ="fornumid",many = @Many(select ="com.msit.ztkj.praise.mapper.PraiseMapper.getAllPraiseByTargetid"))})
    List<Fornum> getAllFornum();



    @Select("select * from fornum where userid=#{userid} and status=#{status} order by sendtime desc")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Fornum> getMyFornum(Fornum fornum);


}
