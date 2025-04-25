package com.msit.ztkj.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class FileUtils {

    public static String getSavePath(){
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String savedDir =  path.getAbsolutePath().replace("target\\classes","src\\main\\webapp\\file").toString();
            return savedDir;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String save(MultipartFile[] pictures){
        String savedDir = getSavePath();
        StringBuffer sb = new StringBuffer();

        try{

            for (MultipartFile picture : pictures) {

                // 截取文件的扩展名(如.jpg)
                String oriName = System.currentTimeMillis()+picture.getOriginalFilename();
                sb.append(oriName+"#");
                File file = new File(savedDir,oriName);

                if(!file.exists()){ file.createNewFile();}
                // 保存文件
                picture.transferTo(file);
//                System.out.println(file.getAbsolutePath()+"-------->");
            }
            if (sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        catch (Exception e) {
            return "-1";
        }


        return sb.toString();
    }

    public static String save(String savedDir,MultipartFile[] pictures){
        StringBuffer sb = new StringBuffer();

        try{

            for (MultipartFile picture : pictures) {

                // 截取文件的扩展名(如.jpg)
                String oriName = System.currentTimeMillis()+picture.getOriginalFilename();
                sb.append(oriName+"#");
                File file = new File(savedDir,oriName);

                if(!file.exists()){ file.createNewFile();}
                // 保存文件
                picture.transferTo(file);
//                System.out.println(file.getAbsolutePath()+"-------->");
            }
            if (sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        catch (Exception e) {
            return "-1";
        }


        return sb.toString();
    }
}
