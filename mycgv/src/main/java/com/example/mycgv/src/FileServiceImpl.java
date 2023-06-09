package com.example.mycgv.src;

import com.example.mycgv.src.board.model.PostBoardReq;
import com.example.mycgv.src.movie.Movie;
import com.example.mycgv.src.notice.model.Notice;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl {

    @Value("${spring.file.dir}")
    private String fileDir;

    public void multiFileSave(Movie movie, HttpServletRequest request) throws Exception {
        for (int i = 0; i < movie.getFiles().length; i++) {
            MultipartFile file = movie.getFiles()[i];

            if(!file.getOriginalFilename().equals("")) {
                String path = fileDir;
                File upload_file = new File(path + File.separator + movie.getMsfiles().get(i));
                file.transferTo(upload_file);
            }
        }
    }

    public Movie multiFileCheck(Movie movie) {
        for(MultipartFile file : movie.getFiles()) {
            if(!file.getOriginalFilename().equals("")) {
                UUID uuid = UUID.randomUUID();

                movie.getMfiles().add(file.getOriginalFilename());
                movie.getMsfiles().add(uuid+"_"+file.getOriginalFilename());
            }else {
                movie.getMfiles().add("");
                movie.getMsfiles().add("");
            }
        }

        movie.setMfile1(movie.getMfiles().get(0));
        movie.setMfile2(movie.getMfiles().get(1));
        movie.setMsfile1(movie.getMsfiles().get(0));
        movie.setMsfile2(movie.getMsfiles().get(1));

        return movie;
    }

    public void fileDelete(PostBoardReq postBoardReq, HttpServletRequest request) throws Exception {
        if(postBoardReq.getBsfile() != null) {
            String path = fileDir;

            File old_file = new File(path + File.separator + postBoardReq.getBsfile());
            if(old_file.exists()) {
                old_file.delete();
            }
        }
    }

    public void fileDelete(Notice notice, HttpServletRequest request) throws Exception {
        if(notice.getNsfile() != null) {
            String path = fileDir;

            File old_file = new File(path + File.separator + notice.getNsfile());
            if(old_file.exists()) {
                old_file.delete();
            }
        }
    }


    /**
     * 1. file1 필드에서 원본 파일 이름을 가져옴
     * 2. 파일 이름이 빈 문자열이 아닌 경우 파일을 저장할 경로를 지정
     * 3. (path 현재 웹 애플리케이션의 루트 디렉토리 경로를 반환) 그 다음 경로를 \\upload\\로 지정합니다.
     * 4. 새 파일 객체를 생성 후 업로드된 파일을 해당 경로에 저장
     * 5. 그리고 이전 파일을 나타내는 old_filename 문자열을 사용하여 이전 파일 객체를 생성 해당 파일이 존재하는 경우
     *    ofile.exists()가 참을 반환하므로, ofile.delete()를 사용하여 이전 파일을 삭제
     */
    public void updateFileSave(PostBoardReq postBoardReq, HttpServletRequest request, String old_filename) throws Exception {
        if(!postBoardReq.getFile1().getOriginalFilename().equals("")) {
            String path = fileDir;
            File file = new File(path + postBoardReq.getBsfile());
            postBoardReq.getFile1().transferTo(file);

            File ofile = new File(path + old_filename);
            if(ofile.exists()) {
                ofile.delete();
            }
        }
    }
    public void updateFileSave(Notice notice, HttpServletRequest request, String old_filename) throws Exception {
        if(!notice.getFile1().getOriginalFilename().equals("")) {
            String path = fileDir;
            File file = new File(fileDir + notice.getNsfile());
            notice.getFile1().transferTo(file);

            File ofile = new File(path + old_filename);
            if(ofile.exists()) {
                ofile.delete();
            }
        }
    }


    /**
     * 1. file1의 필드 null 체크
     * 2. null 이 아니면 파일 이름이 빈 문자열이 아닌지 확인
     * 3. 만약 비어있지 않다면 UUID를 생성한 후, bfile 필드에는 파일 이름을 bs 필드에는 UUID 와 파일이름을 조합한 값을 사용
     */
    public PostBoardReq updateFileCheck(PostBoardReq postBoardReq) {
        if(postBoardReq.getFile1() != null) {
            if(!postBoardReq.getFile1().getOriginalFilename().equals("")) {
                UUID uuid = UUID.randomUUID();
                postBoardReq.setBfile(postBoardReq.getFile1().getOriginalFilename());
                postBoardReq.setBsfile(uuid + "_" + postBoardReq.getFile1().getOriginalFilename());
            }
        }
        return postBoardReq;
    }

    public Notice updateFileCheck(Notice notice) {
        if(notice.getFile1().getOriginalFilename().equals("")) {
            if(!notice.getFile1().getOriginalFilename().equals("")) {
                UUID uuid = UUID.randomUUID();
                notice.setNfile(notice.getFile1().getOriginalFilename());
                notice.setNsfile(uuid + "_" + notice.getFile1().getOriginalFilename());
            }
        }
        return notice;
    }

    /**
     * 1. file1 필드 값 여부 체크
     * 2. 비어있는 경우 bs, b file 필드 빈문자열 설정
     * 3. 값이 첨부되어 있으면 UUID 생성 후 bfile 에는 파일이름 bsfile 은 UUID와 파일 이름을 조합한 값을 설정
     */
    public PostBoardReq fileCheck(PostBoardReq postBoardReq) {
        if(postBoardReq.getFile1().getOriginalFilename().equals("")) {
            postBoardReq.setBfile("");
            postBoardReq.setBsfile("");
        }else {
            UUID uuid = UUID.randomUUID();
            postBoardReq.setBfile(postBoardReq.getFile1().getOriginalFilename());
            postBoardReq.setBsfile(uuid + "_" + postBoardReq.getFile1().getOriginalFilename());
        }

        return postBoardReq;
    }

    public Notice fileCheck(Notice notice) {
        if(notice.getFile1().getOriginalFilename().equals("")) {
            notice.setNfile("");
            notice.setNsfile("");
        }else {
            UUID uuid = UUID.randomUUID();
            notice.setNfile(notice.getFile1().getOriginalFilename());
            notice.setNsfile(uuid + "_" + notice.getFile1().getOriginalFilename());
        }
        return notice;
    }

    /**
     * 1. file1 필드에서 원본 파일 이름을 가져온다. 그 후 파일 이름이 빈 문자열이 아닌 경우, 파일 저장 경로를 지정
     * 2. (path 현재 웹 애플리케이션의 루트 디렉토리 경로를 반환) 그 다음 경로를 \\upload\\로 지정합니다.
     * 3. 마지막으로 파일 객체를 생성하고 postBoardReq.getFile1().transferTo(file)를 사용하여 업로드된 파일을 해당 경로에 저장
     * 4. 이 때 파일 이름은 bsfile 의 값으로 설정
     */
     public void fileSave(PostBoardReq postBoardReq, HttpServletRequest request) throws Exception {
        if(!postBoardReq.getFile1().getOriginalFilename().equals("")) {
            String path = fileDir;
            File file = new File(path + File.separator + postBoardReq.getBsfile());
            postBoardReq.getFile1().transferTo(file);
            log.info("file save to: {}", file.getAbsoluteFile()); //로그 출력
        }
    }

    public void fileSave(Notice notice, HttpServletRequest request) throws Exception {
         if(!notice.getFile1().getOriginalFilename().equals("")) {
             String path = fileDir;
             File file = new File(path + File.separator + notice.getNsfile());
             notice.getFile1().transferTo(file);
         }
    }


}
