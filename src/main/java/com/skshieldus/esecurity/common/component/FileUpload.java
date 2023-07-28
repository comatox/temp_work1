package com.skshieldus.esecurity.common.component;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUpload {

    @Value("${file.upload.path}")
    private String basePath;

    /**
     * 파일 업로드
     *
     * @param uploadFile MultipartFile
     * @param directoryCode 디렉토리 구분자 [bbs, movie, empCard, orgVisit, passExcpt, safetycar, safetycar, inoutwrite]
     * @return fileName 파일명, filePath 저장된 파일경로, fileId 파일 고유ID, result(message) 결과 메시지
     */
    public Map<String, Object> uploadFile(MultipartFile uploadFile, String directoryCode) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        //현재 date 세팅
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String currentDate = date.format(formatter);

        //허용되는 확장자 list
        List<String> allowExtArr = new ArrayList<>(Arrays.asList(".jpg", ".png", ".jpeg", ".gif", ".bmp", ".wav", ".wma", ".avi", ".mp3", ".mp4", ".asf", ".mpeg", ".wmv", ".hwp", ".txt", ".doc", ".xls", ".ppt", ".xlsx", ".docx", ".pptx", ".pdf"));

        try {
            java.io.File folder = new java.io.File(basePath, directoryCode.toLowerCase() + "\\" + currentDate);

            if (!folder.exists()) {
                folder.mkdirs();
                log.info("디렉토리가 생성되었습니다");
            }
            else {
                log.info("이미 디렉토리가 존재합니다");
            }

            //기존 파일명
            String fileName = uploadFile.getOriginalFilename();
            //파일의 확장자 파싱(. 포함)
            String fileExt = Objects.requireNonNull(uploadFile.getOriginalFilename()).substring(uploadFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();
            //파일 basePath 경로 세팅
            String directory = folder.getPath();
            //파일 고유 ID 생성(UUID)
            String fileId = directoryCode.toUpperCase() + "_" + currentDate + UUID.randomUUID() + fileExt;
            //파일 실제저장 경로
            String filePath = Paths.get(directory, fileId).toString();
            //파일정보가 DB에 저장되는 전제정보
            String fullPath = filePath + ";" + fileId + ";" + fileName;

            //확장자 validation check
            if (!allowExtArr.contains(fileExt)) {
                resultMap.put("result", "notAllowExt");
                resultMap.put("fileName", fileName);
                resultMap.put("filePath", basePath);
                resultMap.put("fileId", fileId);
                resultMap.put("fullPath", fullPath);
                return resultMap;
            }

            //stream으로 file data 저장
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            bufferedOutputStream.write(uploadFile.getBytes());
            bufferedOutputStream.close();

            //data return
            resultMap.put("result", "success");
            resultMap.put("fileName", fileName);
            resultMap.put("filePath", basePath);
            resultMap.put("fileId", fileId);
            resultMap.put("fullPath", fullPath);
        } catch (Exception e) {
            log.info("exception : {}", e.getMessage());

            resultMap.put("result", e.getMessage());
            resultMap.put("fileName", null);
            resultMap.put("filePath", null);
            resultMap.put("fileId", null);
            resultMap.put("fullPath", null);

            return resultMap;
        }
        return resultMap;
    }

    /**
     * 파일 삭제
     *
     * @param filePath 파일 전체 경로
     * @return Map<String, Object>
     */
    public Map<String, Object> deleteFile(String filePath) {
        Map<String, Object> resultMap = new HashMap<>();

        java.io.File file = new java.io.File(filePath);

        try {
            if (file.exists()) {
                if (file.delete()) {
                    resultMap.put("result", "success");
                    resultMap.put("filePath", filePath);
                }
                else {
                    resultMap.put("result", "fail");
                    resultMap.put("filePath", filePath);
                }
            }
            else {
                resultMap.put("result", "notExist");
                resultMap.put("filePath", filePath);
            }
        } catch (Exception e) {
            log.info("exception : {}", e.getMessage());

            resultMap.put("result", e.getMessage());
            resultMap.put("filePath", filePath);

            return resultMap;
        }

        return resultMap;
    }

}
