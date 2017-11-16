package cn.com.kuaidian.resource.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.gelivable.web.EnvUtils;

import cn.com.kuaidian.util.SpringUtils;
import cn.com.kuaidian.util.StringUtils;

import com.danga.MemCached.MemCachedClient;

public class MemcachedTag extends BodyTagSupport implements TryCatchFinally {
	
    private static final long serialVersionUID = 414530104426423187L;
    long time = 3600;
    String key;
    String content;
    String refresh;
    
    public void setTime(long time) {
        this.time = time;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	private String getKey() {
        if (key != null) {
            return key;
        }
        String queryString  = EnvUtils.getEnv().getRequest().getQueryString();
        String path =  EnvUtils.getEnv().getRequest().getServletPath();
        if(StringUtils.isEmpty(queryString)){
        	return path;
        }else{
        	return StringUtils.concat(path,"?",queryString);
        }
    }

    @Override
    public int doStartTag() throws JspException {
    	if("true".equalsIgnoreCase(refresh)){
    		return EVAL_BODY_BUFFERED;
    	}
        content = (String) SpringUtils.getBean("mcc", MemCachedClient.class).get(getKey());
        if (content != null) {
            try {
                pageContext.getOut().write(content);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return SKIP_BODY;
        }
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
        	content = bodyContent.getString();
        	SpringUtils.getBean("mcc", MemCachedClient.class).set(getKey(), content, new java.util.Date(System.currentTimeMillis() + time * 1000));
        	bodyContent.clearBody();
            bodyContent.write(content);
            bodyContent.writeOut(bodyContent.getEnclosingWriter());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return SKIP_BODY;
    }

    @Override
    public void doCatch(Throwable ex) throws Throwable {
        throw ex;
    }

    @Override
    public void doFinally() {
        key = null;
        content = null;
    }
}
