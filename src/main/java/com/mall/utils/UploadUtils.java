package com.mall.utils;

import java.io.File;

/**
 * @author lly
 */
public class UploadUtils {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String IMG_PATH_PREFIX_WIN = "E:";
    public final static String IMG_PATH_PREFIX_LINUX = "/usr";
    public final static String IMG_PATH_SUFFIX = "/static/upload/";

    public static File getImgDirFile(){
        String fileDirPath;
        //若当前系统是window系统
        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
            fileDirPath = IMG_PATH_PREFIX_WIN + IMG_PATH_SUFFIX;
        }else{
            //若当前系统是linux系统
            fileDirPath = IMG_PATH_PREFIX_LINUX + IMG_PATH_SUFFIX;
        }
        // 构建上传文件的存放 "文件夹" 路径
        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

}
