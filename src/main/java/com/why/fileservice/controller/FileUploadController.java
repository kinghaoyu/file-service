package com.why.fileservice.controller;

import com.why.fileservice.entity.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述:
 * 文件控制器
 *
 * @author why 0005412
 * @date 2021-06-21
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileUploadController {
    // 读取配置文件中的路径 static/files
    @Value("${fileLocation}")
    private String fileLocation;
    @Value("${server.port}")
    private int port;
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @PostMapping("/upload")
    @ResponseBody
    public ResponseData upload(HttpServletRequest request){
        List<MultipartFile>  fileList = ((MultipartHttpServletRequest) request).getFiles("files");
        List<String> list = new LinkedList<>();
        for (MultipartFile file : fileList) {
            try {
                String fileName = uploadFile(file);
                if(fileName != null && !"".equals(fileName)){
                    String tempName = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/file/get?fileName=" + fileName;
                    log.info("上传成功，文件名为： " + tempName);
                    list.add(tempName);
                }
            } catch (Exception e) {
                log.error("上传文件错误！",e);
            }
        }
        return ResponseData.success(list);
    }
    public String uploadFile(MultipartFile file) throws Exception {
        // 获得 classpath 的绝对路径
        String realPath = new ApplicationHome(getClass()).getSource().getParentFile().toString() +"/" + fileLocation;
        log.info("realPath: " + realPath);
        File newFile = new File(realPath);
        // 如果文件夹不存在、则新建
        if (!newFile.exists()) {
            newFile.mkdirs();
            log.info("文件目录不存在，已经创建成功：realPath :" + realPath);
        }
        // 上传
        String fileName = format.format(new Date()) +"_" + file.getOriginalFilename();
        file.transferTo(new File(newFile, fileName));
        log.info("fileName: " + fileName);
        return fileName;
    }


    @GetMapping("/get")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        // 获得待下载文件所在文件夹的绝对路径
        String realPath = new ApplicationHome(getClass()).getSource().getParentFile().toString() + "/"  + fileLocation;
        // 获得文件输入流
        FileInputStream inputStream = new FileInputStream(new File(realPath, fileName));
        // 设置响应头、以附件形式打开文件
        response.setHeader("content-disposition", "attachment; fileName=" + new String(fileName.getBytes(),"utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        while ((len = inputStream.read(data)) != -1) {
            outputStream.write(data, 0, len);
        }
        outputStream.close();
        inputStream.close();
    }
}
