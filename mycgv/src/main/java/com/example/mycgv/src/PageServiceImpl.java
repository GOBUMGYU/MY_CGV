package com.example.mycgv.src;

import com.example.mycgv.src.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class PageServiceImpl {

    private UserServiceImpl userService;

    public Map<String, Integer> getPageResult(String rpage, String serviceName, Object service) {
        Map<String, Integer> param = new HashMap<String, Integer>();

        int startCount = 0;
        int endCount = 0;
        int pageSize = 3;
        int reqPage = 1;
        int pageCount = 1;
        int dbCount = 0;

        if(serviceName.equals("user")) {
            userService = (UserServiceImpl) service;
            dbCount = userService.getTotalCount();
        }

        if(dbCount % pageSize == 0) {
            pageCount = dbCount / pageSize;
        }else {
            pageCount = dbCount / pageSize + 1;
        }

        if(rpage != null) {
            reqPage = Integer.parseInt(rpage);
            startCount = (reqPage - 1) * pageSize + 1;
            endCount = reqPage * pageSize;
        }else {
            startCount = 1;
            endCount = pageSize;
        }

        param.put("startCount", startCount);
        param.put("endCount", endCount);
        param.put("dbCount", dbCount);
        param.put("pageSize", pageSize);
        param.put("rpage", reqPage);
        param.put("pageCount", pageCount);

        return param;
    }
}
