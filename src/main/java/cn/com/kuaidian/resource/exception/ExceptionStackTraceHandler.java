package cn.com.kuaidian.resource.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常信息打印控制台工具类
 * Created by ilinfei on 16/6/14.
 */
@ControllerAdvice
public class ExceptionStackTraceHandler {

    @ExceptionHandler(Exception.class)
    public String printStackTrace(HttpServletRequest request, HttpServletResponse response, Exception e){
        e.printStackTrace();
        if(request.getRequestURI().indexOf("/admin/") == 0){
            response.setStatus(500);
            return null;
        } else {
            return "forward:/exception/error";
        }
    }
}
