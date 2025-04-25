package com.msit.ztkj.attence.mapper;

import com.msit.ztkj.attence.model.Attence;
import com.msit.ztkj.userinfo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttenceMapper {

    @Insert("insert into attence(userid,address,remark1,remark2,type) values(#{userid},#{address},#{remark1},#{remark2},#{type})")
    int insert(Attence attence);

    @Delete("delete from attence where attenceid=#{attenceid}")
    int delete(Attence attence);

    @Select("SELECT * FROM attence WHERE userid=#{userid} and DATEDIFF(time,NOW())=0")
    List<Attence> checkAttence(Attence attence);

    @Select("select * from attence where type=0")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Attence> getAllAttence();
    @Select("select * from attence where type=1")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Attence> getAllAttence2();

    @Select("SELECT * FROM attence WHERE  DATEDIFF(time,NOW())=0 and userid IN (SELECT userid FROM USER WHERE userno=1 AND userLevel=1)")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Attence> getAllAttence3(User user);

    @Select("select * from attence where userid=#{userid}")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Attence> getAllMyAttence(Attence attence);

    @Select("select * from attence where remark1=#{remark1}")
    @Results({@Result(property = "user",column = "userid",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))})
    List<Attence> getAllByRemark(Attence attence);





}
