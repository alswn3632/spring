package com.ezen.spring.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ezen.spring.domain.FileVO;
import com.ezen.spring.repository.FileMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Component
public class FileSweeper {

    private final FileMapper fileMapper;
    private final String BASE_PATH = "D:\\_myproject\\_java\\_fileUpload\\";

    @Scheduled(cron="00 00 14 * * *")
    public void fileSweeper() {
        log.info(">>>> FileSweeper Running Start > {}", LocalDateTime.now());

        // DB에 있는 파일 정보
        List<FileVO> dbList = fileMapper.getListAllFile();

        List<String> currFiles = new ArrayList<String>();
        for(FileVO fvo : dbList) {
            String filePath = fvo.getSaveDir() + File.separator + fvo.getUuid();
            String fileName = fvo.getFileName();

            currFiles.add(BASE_PATH + filePath + "_" + fileName);

            if(fvo.getFileType() > 0) {
                currFiles.add(BASE_PATH + filePath + "_th_" + fileName);
            }
        }

        // 실제 파일의 경로
        LocalDate now = LocalDate.now();
        String today = now.toString();
        today = today.replace("-", File.separator);

        // 경로를 기반으로 저장되어있는 파일을 검색
        File dir = Paths.get(BASE_PATH+today).toFile();

        // listFiles() : 경로에 있는 모든 파일을 배열로 리턴
        File[] allFileObj = dir.listFiles();

        // 실제 저장되어 있는 파일 목록(2)과, DB의 존재 파일(1)을 비교하여 DB에 없는 파일은 삭제 진행
        for(File file : allFileObj) {
            String storedFileName = file.toPath().toString();

            if(!currFiles.contains(storedFileName)) {
                log.info(">>>> FileSweeper : delete file > {}", storedFileName);
                file.delete();
            }
        }

        log.info(">>>> FileSweeper Running End > {}", LocalDateTime.now());
    }

}