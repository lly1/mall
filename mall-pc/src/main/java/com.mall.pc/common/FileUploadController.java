package com.mall.common;

import com.mall.api.utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author lly
 */

@RestController
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    /**
     * 上传图片
     * @param file 图片文件
     * @return 上传状态或图片名称
     */
    @PostMapping("/upPic")
    public String getFile(@RequestParam("file") MultipartFile file) {
        logger.info("收到上传图片请求");
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(UploadUtils.getImgDirFile().getAbsolutePath() + File.separator + fileName);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            String rtnPath = UploadUtils.IMG_PATH_SUFFIX +fileName;
            logger.info("图片上传地址:"+dest.getAbsolutePath());
            logger.info("图片请求地址:"+rtnPath);
            return rtnPath;
        } catch (IOException e) {
            System.out.println("上传失败");
            System.out.println(e.getMessage());
        }
        return "上传失败！";
    }
}
