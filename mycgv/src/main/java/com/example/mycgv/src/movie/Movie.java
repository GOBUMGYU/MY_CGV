package com.example.mycgv.src.movie;

import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    private Long mid;
    private String mcategory;
    private String mname;
    private String mdesc;
    private String mfile1;
    private String mfile2;
    private String msfile1;
    private String msfile2;
    @Timestamp
    private String createAt;
    @UpdateTimestamp
    private String updateAt;

    MultipartFile[] files;
    ArrayList<String> mfiles = new ArrayList<String>();
    ArrayList<String> msfiles = new ArrayList<String>();
}
