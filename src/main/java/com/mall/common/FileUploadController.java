package com.mall.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public String getFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        logger.info("收到上传图片请求");
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String relativePath = "/static/upload/";
        String filePath = "src/main/resources" + relativePath;
        //如果目录不存在，自动创建目录
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        logger.info("filePath = " + dir.getAbsolutePath());
        File dest = new File(dir.getAbsolutePath() + File.separator + fileName);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            String rtnPath = relativePath +fileName;
            logger.info("图片请求地址:"+rtnPath);
            return rtnPath;
        } catch (IOException e) {
            System.out.println("上传失败");
            System.out.println(e.getMessage());
        }
        return "上传失败！";
    }
}
