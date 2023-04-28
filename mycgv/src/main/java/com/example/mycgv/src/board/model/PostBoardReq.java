package com.example.mycgv.src.board.model;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Getter
@Setter
public class PostBoardReq {

    private Long bid;
    private String btitle;
    private String bcontent;

    private String bfile;
    private String bsfile;
    private int bhits;
    @Timestamp
    private String createAt;
    @UpdateTimestamp
    private String updateAt;
    private MultipartFile file1;

}
