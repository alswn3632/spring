package com.ezen.spring.handler;

import com.ezen.spring.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileRemoveHandler {
    private final String BASE_PATH = "D:\\_myproject\\_java\\_fileUpload\\";

    public int deleteFile(FileVO fvo) {
        boolean isDel = false;

        File dir = new File(BASE_PATH, fvo.getSaveDir());

        // 원본 파일 삭제
        File file = new File( dir, fvo.getUuid() + "_" + fvo.getFileName());

        if(file.exists()) {
            log.info(">>>> FileRemoveHandler: delete file > {}", fvo.getUuid() + "_" + fvo.getFileName());
            isDel = file.delete();
        }

        // 썸네일 파일 삭제
        if(fvo.getFileType() > 0){
            File thFile = new File(dir, fvo.getUuid() + "_th_" + fvo.getFileName());
            if(thFile.exists()){
                log.info(">>>> FileRemoveHandler: delete file > {}", fvo.getUuid() + "_th_" + fvo.getFileName());
                isDel = thFile.delete();
            }
        }

        return isDel? 1:0;
    }

}
