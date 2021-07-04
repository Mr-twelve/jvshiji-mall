package com.shier.mall.controller.common;

import com.shier.mall.common.Constants;
import com.shier.mall.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/12
 */
@Controller
public class UploadController {

    public Result upload(MultipartFile file) {
        if (file.isEmpty()) {
            return new Result().getError("上传失败");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url ="/upload/" + newFileName;
        return new Result().getSuccess(url);
    }

    /**
     * layui富文本文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/layeditupload")
    @ResponseBody
    public Result layeditupload( MultipartFile file) {
        if (file.isEmpty()) {
            return new Result().getError("上传失败");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String src = "/upload/" + newFileName;
        String title = newFileName;
        Map<String,String> data = new HashMap<>();
        data.put("src",src);
        data.put("title",title);
        return new Result().getSuccess(data);
    }

}
