package com.msit.ztkj.praise.mapper;

import com.msit.ztkj.praise.model.Praise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PraiseMapper {

    @Insert("insert into praise(userid,targetid) values(#{userid},#{targetid})")
    int insertPraise(Praise praise);

    @Delete("delete from praise where praiseid=#{praiseid}")
    int deletePraise(Praise praise);

    @Delete("delete from praise where userid=#{userid} and targetid=#{targetid}")
    int deleteMyPraise(Praise praise);

    @Select("select * from praise where userid=#{userid}")
    List<Praise> getAllPraiseByUserid(Praise praise);

    @Select("select * from praise where targetid=#{targetid}")
    List<Praise> getAllPraiseByTargetid(Praise praise);

}
