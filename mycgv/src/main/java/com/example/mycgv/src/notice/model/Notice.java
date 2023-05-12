package com.example.mycgv.src.notice.model;


import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notice {

    private Long nid;
    private String ntitle;
    private String ncontent;

    private String nfile;
    private String nsfile;
    private int nhits;
    @Timestamp
    private String createAt;
    @UpdateTimestamp
    private String updateAt;
    private MultipartFile file1;
}
