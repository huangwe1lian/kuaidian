package cn.com.kuaidian.web.admin.swfupload;

import cn.com.kuaidian.util.PropertiesUtils;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * UPC上传组件
 * Created by ilinfei on 16/6/3.
 */
@Controller
public class SwfUploadController {

    @Autowired
    private PropertiesUtils propertiesUtils;

    @RequestMapping("/uploadImage")
    public ModelAndView uploadImage(){
        ModelAndView mv = new ModelAndView("/admin/swfupload/upload_image");
        mv.addObject("upc.url", propertiesUtils.getUpcPropertiesVal("upc.url"));
        mv.addObject("upc.referer", propertiesUtils.getUpcPropertiesVal("upc.referer"));

        return mv;
    }

    @RequestMapping("/uploadImage/demo")
    public ModelAndView demo(){
        ModelAndView mv = new ModelAndView("/admin/swfupload/demo");

        return mv;
    }
    
    @RequestMapping("/uploadImage/ckeditor")
    public ModelAndView ckeditor(){
    	ModelAndView mv = new ModelAndView("/admin/swfupload/ckeditor_upload_image");
    	Env env = EnvUtils.getEnv();
    	String id = env.param("id");
    	mv.addObject("id",id);
    	return mv;
    }
}
