package com.msit.ztkj.xiu.mapper;

import com.msit.ztkj.xiu.model.Xiu;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface XiuMapper {

    //添加
    @Insert("insert into xiu(xiuid,title,stime,etime,userid,remark1) " +
            "values(#{xiuid},#{title},#{stime},#{etime},#{userid},#{remark1})")
    int insertXiu(Xiu xiu);

    //删除
    @Delete("delete from xiu where xiuid=#{ xiuid }")
    int deleteXiu(Xiu xiu);

    //获取所有
    @Select("<script>" +
            "select * from xiu where title like concat('%',#{title},'%')" +
            "<if test='userid!=null'> and userid=#{userid}</if>" +
            "</script>")
    List<Xiu> getAllXiu(Xiu xiu);


     //修改用户信息
    @Update({"<script>"+
            "update xiu " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='xiuid!=null'>xiuid=#{xiuid},</if><if test='title!=null'>title=#{title},</if><if test='stime!=null'>stime=#{stime},</if><if test='etime!=null'>etime=#{etime},</if><if test='userid!=null'>userid=#{userid},</if><if test='remark1!=null'>remark1=#{remark1},</if>"+
            "</trim>"+
            "where xiuid=#{ xiuid }"+
            "</script>"})
    int updateXiu(Xiu xiu);


    //通过id获取信息
    @Select("select * from xiu where xiuid=#{ xiuid }")
    Xiu getXiu(Xiu xiu);

   @Select("<script>" +
            "select * from xiu where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            " limit #{start},#{pageSize}" +
            "</script>")
    List<Xiu> getAllXiuByPage(Xiu xiu);

   //统计记录数
   @Select("<script>" +
            "select count(*) from xiu where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            "</script>")
    int totalXiu(Xiu xiu);
}