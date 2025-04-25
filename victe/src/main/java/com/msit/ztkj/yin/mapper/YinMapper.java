package com.msit.ztkj.yin.mapper;

import com.msit.ztkj.yin.model.Yin;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface YinMapper {

    //添加
    @Insert("insert into yin(yinid,title,pics,re,dan,tang,gai,wei,qian,sendtime) " +
            "values(#{yinid},#{title},#{pics},#{re},#{dan},#{tang},#{gai},#{wei},#{qian},#{sendtime})")
    int insertYin(Yin yin);

    //删除
    @Delete("delete from yin where yinid=#{ yinid }")
    int deleteYin(Yin yin);

    //获取所有
    @Select("<script>" +
            "select * from yin where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            "</script>")
    List<Yin> getAllYin(Yin yin);

    @Select("<script>" +
            "select * from yin order by rand() limit 5 where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            "</script>")
    List<Yin> getAllYin2(Yin yin);


     //修改用户信息
    @Update({"<script>"+
            "update yin " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='yinid!=null'>yinid=#{yinid},</if><if test='title!=null'>title=#{title},</if><if test='pics!=null'>pics=#{pics},</if><if test='re!=null'>re=#{re},</if><if test='dan!=null'>dan=#{dan},</if><if test='tang!=null'>tang=#{tang},</if><if test='gai!=null'>gai=#{gai},</if><if test='wei!=null'>wei=#{wei},</if><if test='qian!=null'>qian=#{qian},</if><if test='sendtime!=null'>sendtime=#{sendtime},</if>"+
            "</trim>"+
            "where yinid=#{ yinid }"+
            "</script>"})
    int updateYin(Yin yin);


    //通过id获取信息
    @Select("select * from yin where yinid=#{ yinid }")
    Yin getYin(Yin yin);

   @Select("<script>" +
            "select * from yin where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            " limit #{start},#{pageSize}" +
            "</script>")
    List<Yin> getAllYinByPage(Yin yin);

   //统计记录数
   @Select("<script>" +
            "select count(*) from yin where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            "</script>")
    int totalYin(Yin yin);
}