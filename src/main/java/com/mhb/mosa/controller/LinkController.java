package com.mhb.mosa.controller;

import com.mhb.mosa.service.LinkService;
import com.mhb.mosa.util.ResultBeanUtils;
import com.mhb.mosa.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @date: 2021/6/10 9:32
 */
@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping("/login/{userName}")
    public ResultBean login(HttpServletRequest request, @PathVariable String userName) {
        ResultBean result;
        try {
            boolean login = linkService.login(userName);
            if (login) {
                result = ResultBeanUtils.success();
            } else {
                result = ResultBeanUtils.failed();
            }
        } catch (Exception e) {
            result = ResultBeanUtils.exception();
            log.error("登入-异常：", e);
        }
        return result;
    }

}
