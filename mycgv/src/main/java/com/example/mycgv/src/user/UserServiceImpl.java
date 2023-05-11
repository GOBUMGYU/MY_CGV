package com.example.mycgv.src.user;

import com.example.mycgv.src.SessionVO;
import com.example.mycgv.src.user.model.PostUserReq;
import com.example.mycgv.src.user.model.User;
import com.example.mycgv.utils.JwtService;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//Service create, update, delete 의 로직처리
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * 회원가입
     */
    @Override
    public int createUser(PostUserReq postUserReq) {
        return userRepository.createUser(postUserReq);
    }

    @Override
    public int getTotalCount() {
        return userRepository.totalCount();
    }

    @Override
    public List userList(int startCount, int endCount) {
        List<PostUserReq> list = userRepository.selectAll(startCount, endCount);
        return list;
    }

    @Override
    public PostUserReq content(Long idx) {
        return userRepository.selectContent(idx);
    }

    @Override
    public int getIdCheck(String id) {
        return userRepository.idCheck(id);
    }


    @Override
    public SessionVO getLoginResult(PostUserReq vo) {
        return userRepository.select(vo);
    }
/*
    @Override
    public User login(String id) {
        return userRepository.getPwd(id);

    }*/
}
