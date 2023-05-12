package com.example.mycgv.src.user;

import com.example.mycgv.src.SessionVO;
import com.example.mycgv.src.user.model.PostUserReq;
import com.example.mycgv.src.user.model.User;
import com.example.mycgv.utils.JwtService;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@AllArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    /**
     * 아이디 중복 처리 체크
     */
    @ResponseBody
    @GetMapping("/id_check")
    public String idCheck(String id) {
        int result = userService.getIdCheck(id);
        return String.valueOf(result);
    }

    /**
     * 회원가입 Form
     */
    @GetMapping("/new")
    public String JoinForm() {
        return "join/join";
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/new")
    public ModelAndView createUser(@Valid PostUserReq postUserReq) {
        ModelAndView mv = new ModelAndView();
        int result = userService.createUser(postUserReq);
        if(result == 1) {
            mv.addObject("join_result", "ok");
            mv.setViewName("redirect:/users/login");
        }else  {
            mv.setViewName("errorPage");
        }
        return mv;
    }

    /**
     * login Form
     */
    @GetMapping("/login")
    public ModelAndView LoginForm(String auth) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("auth", auth);
        mv.setViewName("/login/login");
        return mv;
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public ModelAndView login(PostUserReq vo, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        SessionVO svo = userService.getLoginResult(vo);

        if(svo != null) {
            if(svo.getLoginresult() == 1) {
                session.setAttribute("svo", svo);
                mv.addObject("login_result", "ok");
                mv.setViewName("/index");
            }
        }else {
            mv.addObject("login_result", "fail");
            mv.setViewName("/login/login");
        }
        return mv;
    }

    /**
     * logout 처리
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mv = new ModelAndView();

        SessionVO svo = (SessionVO) session.getAttribute("svo");

        if(svo != null) {
            session.invalidate();
            mv.addObject("logout_result", "ok");
        }
        mv.setViewName("/index");

        return mv;
    }
}
