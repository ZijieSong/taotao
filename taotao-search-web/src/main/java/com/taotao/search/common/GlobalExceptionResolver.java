package com.taotao.search.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //打印错误日志
        log.error("error message:", e);
        //返回错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统有点累～～");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
