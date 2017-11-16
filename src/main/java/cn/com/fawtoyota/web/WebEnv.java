package cn.com.fawtoyota.web;

import cn.com.fawtoyota.util.StringUtils;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;

/**
 * 请求环境上下文
 * Created by ilinfei on 16/5/31.
 */
public class WebEnv {
    public Env env;

    public WebEnv(){
        this.env = EnvUtils.getEnv();
    }

    private String getParameter(String name){
        String value = this.env.getRequest().getParameter(name);
        value = StringUtils.xssCharFilter(StringUtils.trim(value));

        return value;
    }

    public String param(String name) {
        return this.getParameter(name);
    }

    public String param(String name, String def) {
        String v = this.getParameter(name);
        return v == null ? def : v;
    }

    public int paramInt(String name) {
        return paramInt(name, 0);
    }

    public int paramInt(String name, int def) {
        String v = this.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public long paramLong(String name) {
        return paramLong(name, 0);
    }

    public long paramLong(String name, long def) {
        String v = this.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Long.parseLong(this.getParameter(name));
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public double paramDouble(String name) {
        return paramDouble(name, 0.0);
    }

    public double paramDouble(String name, double def) {
        String v = this.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Double.parseDouble(this.getParameter(name));
        } catch (NumberFormatException ex) {
            return def;
        }
    }
}
