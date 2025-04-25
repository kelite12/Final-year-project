package com.msit.ztkj.banner.mapper;

import com.msit.ztkj.banner.model.Banners;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface BannersMapper {
    // 添加banner
    @Insert("insert into banners(pos,pic,status) " +
            "values(#{pos},#{pic},#{status})")
    int addBanners(Banners banners);

    // 删除banner
    @Delete("delete from banners where bannersid=#{bannersid}")
    int deleteBanners(Banners banners);

    // 查询所有banner
    @Select("select * from banners")
    List<Banners> getAllBanners();

    // 查询能使用banner
    @Select("select * from banners where status=1")
    List<Banners> getUsedBanners();

    // 查询某个banner
    @Select("selece * from banners where bannersid=#{bannersid}")
    Banners getBannersById(int bannersid);

    // 修改banner
    @Update({"<script>"
            + "update banners "
            + "<trim prefix='set' suffixOverrides=','>"
            + "<if test='pos!=null'>pos=#{pos},</if>"
            + "<if test='pic!=null'>pic=#{pic},</if>"
            + "<if test='status!=null'>status=#{status},</if>"
            + "</trim>"
            + " where bannersid=#{bannersid} "
            + "</script>"})
    int updateBanners(Banners banners);
}
