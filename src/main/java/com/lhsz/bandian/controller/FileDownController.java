package com.lhsz.bandian.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.Exception.NoticeException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author lizhiguo
 * 2020/7/22 17:26
 */
@RestController
public class FileDownController {
    @ApiOperation("文件下载")
    @GetMapping("fileDownload")
    public HttpResult fileDownload(HttpServletRequest request, HttpServletResponse response, @ApiParam("文件存储路径") @RequestParam String fileName) {

        File file = new File(fileName);
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        if (!file.exists()) {
            throw new NoticeException("下载失败！文件不存在！");
        }

        try {
            //判断浏览器是否为火狐
            String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (UserAgent != null && UserAgent.indexOf("firefox") != -1) {
                // 火狐浏览器 设置编码new String(realName.getBytes("GB2312"), "ISO-8859-1");
                fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");//encode编码UTF-8 解决大多数中文乱码
                fileName = fileName.replace("+", "%20");//encode后替换空格  解决空格问题
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response.setContentType("application/force-download");//设置强制下载
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);//设置文件名

        byte[] buff = new byte[1024];// 用来存储每次读取到的字节数组
        //创建输入流（读文件）输出流（写文件）
        BufferedInputStream bis = null;
        OutputStream os = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            os = response.getOutputStream();

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return HttpResult.succee();
    }
}
