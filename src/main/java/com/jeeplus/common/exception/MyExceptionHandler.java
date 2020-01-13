package com.jeeplus.common.exception;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 全局拦截器
 *
 * @author gjzuo
 * @version V1.0
 * @date 2019/12/19 8:54
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //文件过大异常处理
        if (ex instanceof MaxUploadSizeExceededException) {
            return new ModelAndView("error/error_fileupload.jsp");
        }
        return null;
    }
}
