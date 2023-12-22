package com.foreign.trade.controller.common;

import com.foreign.trade.config.Constants;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: UploadController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 18:52:00
 **/
@Controller
@RequestMapping("/admin")
public class UploadController {

    @Resource
    private StandardServletMultipartResolver servletMultipartResolver;

    @PostMapping("/upload/file")
    @ResponseBody
    public Result upload(HttpServletRequest request, @RequestParam("file")MultipartFile file) throws URISyntaxException {
        long fileSize = file.getSize();
        if (fileSize > 10 * 1024 * 1024) {
            return new Result(404, "Image is too big.");
        }

        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        // 生成文件名通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random random = new Random();
        String newFileName = sdf.format(new Date()) + random.nextInt(100) + suffixName;
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC_MAIN);
        // 创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC_MAIN + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败，路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            Result successResult = ResultGenerator.genSuccessResult();
            successResult.setData("/upload/main/" + newFileName);

            return successResult;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    @PostMapping("/upload/files")
    @ResponseBody
    public Result uploadMultiFiles(HttpServletRequest request) throws URISyntaxException {
        ArrayList<MultipartFile> multipartFiles = new ArrayList<>(8);
        if (servletMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartRequest.getFileNames();
            int total = 0;
            while (fileNames.hasNext()) {
                if (total > 5) {
                    return ResultGenerator.genFailResult("最多上传5张图片");
                }
                total += 1;
                MultipartFile file = multipartRequest.getFile(fileNames.next());
                multipartFiles.add(file);
            }
        }

        if (CollectionUtils.isEmpty(multipartFiles)) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (multipartFiles.size() > 5) {
            return ResultGenerator.genFailResult("最多上传5张图片");
        }
        List<String> fileNameList = new ArrayList<>(multipartFiles.size());
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 生成文件名通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random random = new Random();
            String newFileName = sdf.format(new Date()) + random.nextInt(100) + suffixName;
            File fileDirectory = new File(Constants.FILE_UPLOAD_DIC_DETAILS);
            // 创建文件
            File destFile = new File(Constants.FILE_UPLOAD_DIC_DETAILS + newFileName);
            try {
                if (!fileDirectory.exists()) {
                    if (!fileDirectory.mkdir()) {
                        throw new IOException("文件夹创建失败，路径为：" + fileDirectory);
                    }
                }
                multipartFile.transferTo(destFile);
                fileNameList.add("/upload/details/" + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultGenerator.genFailResult("文件上传失败");
            }
        }

        Result successResult = ResultGenerator.genSuccessResult();
        successResult.setData(fileNameList);

        return successResult;
    }
}
