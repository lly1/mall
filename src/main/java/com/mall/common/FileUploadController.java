package com.mall.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author lly
 */

@RestController
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);
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
        String filePath = request.getServletContext().getRealPath("/upload/");
        logger.info("filePath = " + filePath);
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            System.out.println("上传成功");
            return fileName;
        } catch (IOException e) {
            System.out.println("上传失败");
            System.out.println(e.getMessage());
        }
        return "上传失败！";
    }
    /**
     * 根据图片名称返回本地图片
     * @param name 图片名称
     * @return 返回本地图片
     */
    @GetMapping(value = "/getPic", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPicture(HttpServletRequest request,@RequestParam("name") String name) {
        logger.info("请求图片");
        String filePath = request.getServletContext().getRealPath("/upload/");
        File file = new File(filePath + name);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
