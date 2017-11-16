package cn.com.fawtoyota.resource.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统异常处理控制类
 * Created by ilinfei on 16/6/1.
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/error")
    public ModelAndView error(){
        ModelAndView mv = new ModelAndView("/exception/error");

        return mv;
    }

    @RequestMapping("404")
    public ModelAndView notFoundFile(){
        ModelAndView mv = new ModelAndView("/exception/404");

        return mv;
    }
}
