package com.skshieldus.esecurity.controller.api.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/common")
public class FileDownloadController {

    /**
     * 파일 다운로드 controller
     *
     * @param filePath 전체경로를 param으로 넘긴다 ex:) C:\\uploadFile\\bbs\\202307\\BBS_2023079417d3ba-95bc-4a9e-884e-5f2879306b2d.jpg
     * @return void
     *
     * @throws IOException
     */
    @ResponseBody
    @GetMapping(value = "/fileDownload")
    public void downloadFile(HttpServletResponse response, @RequestParam("filePath") String filePath) throws IOException {

        File fileDownloadObject = new File(filePath);

        String fileName = fileDownloadObject.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

        //다운로드 방식으로 변경하기 위해서 헤더 설정
        response.setContentLength((int) fileDownloadObject.length());
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        //세팅한 헤더 정보와 파일을 response에 담아서 내보내는 형식
        FileInputStream fileInputStream = new FileInputStream(fileDownloadObject);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int size = -1;

        while ((size = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
            servletOutputStream.write(buffer, 0, size);
        }
        fileInputStream.close();
        servletOutputStream.close();
    }

}
