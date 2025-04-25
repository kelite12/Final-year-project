package com.msit.ztkj.news.mapper;

import com.msit.ztkj.news.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    //添加
    @Insert("insert into news(newsid,title,content,userid,status,readcount,follow) " +
            "values(#{newsid},#{title},#{content},#{userid},#{status},#{readcount},#{follow})")
    int insertNews(News news);

    //删除
    @Delete("delete from news where newsid=#{ newsid }")
    int deleteNews(News news);

    //获取所有
    @Select("select * from news")
    @Results({
            @Result(column = "userid",property = "userid"),
            @Result(column = "userid",property = "user",one = @One(select = "com.msit.ztkj.userinfo.mapper.UserMapper.getUser"))
    })
    List<News> getAllNews(News news);


     //修改用户信息
    @Update({"<script>"+
            "update news " +
            "<trim prefix='set' suffixOverrides=','>"+
            "<if test='newsid!=null'>newsid=#{newsid},</if>" +
            "<if test='title!=null'>title=#{title},</if>" +
            "<if test='content!=null'>content=#{content},</if>" +
            "<if test='sendtime!=null'>sendtime=#{sendtime},</if>" +
            "<if test='userid!=null'>userid=#{userid},</if>" +
            "<if test='status!=null'>status=#{status},</if>" +
            "<if test='readcount!=null'>readcount=#{readcount},</if>" +
            "<if test='follow!=null'>follow=#{follow},</if>"+
            "</trim>"+
            "where newsid=#{ newsid }"+
            "</script>"})
    int updateNews(News news);


    //通过id获取信息
    @Select("select * from news where newsid=#{ newsid }")
    News getNews(News news);

   @Select("<script>" +
            "select * from news where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            " limit #{start},#{pageSize}" +
            "</script>")
    List<News> getAllNewsByPage(News news);

   //统计记录数
   @Select("<script>" +
            "select count(*) from news where 1=1 " +
            "<if test='title!=null'> and title like concat('%',#{title},'%')</if>"+
            "</script>")
    int totalNews(News news);
}