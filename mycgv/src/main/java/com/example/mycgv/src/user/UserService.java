package com.example.mycgv.src.user;

import com.example.mycgv.src.SessionVO;
import com.example.mycgv.src.user.model.PostUserReq;
import com.example.mycgv.src.user.model.User;
import org.hibernate.Session;

import java.util.List;

public interface UserService {

    int getIdCheck(String id);
    int createUser(PostUserReq postUserReq);
    int getTotalCount();


    List userList(int startCount, int endCount);

    public PostUserReq content(Long idx);

    SessionVO getLoginResult(PostUserReq vo);
}
