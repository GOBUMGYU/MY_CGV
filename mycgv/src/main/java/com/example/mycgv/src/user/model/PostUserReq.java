package com.example.mycgv.src.user.model;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {

    private Long idx;
    private String id;
    private String pass;
    private String name;
    private String gender;
    private String email;
    private String email1;
    private String email2;
    private String address;
    private String zonecode;
    private String addr1;
    private String addr2;
    private String hp;
    private String pnum1;
    private String pnum2;
    private String pnum3;
    @Timestamp
    private String createAt;
    @UpdateTimestamp
    private String updateAt;
    private String[] hobby;
    private String pNumber;
    private String hobbyList;
    private String intro;

    private String status;

    public String getEmail() {
        return email1+"@"+email2;
    }

    public String getPnumber() {
        if(pNumber == null) {
            return pnum1+"-"+pnum2+"-"+pnum3;
        }else {
            return pNumber;
        }
    }

    public String getHobbylist() {
        if(hobbyList == null) {
            return String.join(",", hobby);
        }else {
            return hobbyList;
        }
    }

    public void setHobbyList(String hobbyList) {
        this.hobbyList = hobbyList;
    }

    public String getHobbyList() {
        return this.hobbyList;
    }

}
