package cn.com.kuaidian.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;

/**
 * 属性文件加载工具类
 * Created by ilinfei on 16/5/29.
 */
public class PropertiesUtils {

    private List<String> locations;

    private Properties rootProperties;
    private Properties httpProperties;
    private Properties upcProperties;

    private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    public void setLocations(List<String> locations) {
        this.locations = locations;
        this.initProperties();
    }

    private void initProperties(){
        for (String location : this.locations) {
            try {
                Properties properties = PropertiesLoaderUtils.loadAllProperties(location);
                propertiesMap.put(location, properties);

                if(location.equals("root.properties")){
                    rootProperties = properties;
                } else if(location.equals("http.properties")){
                    httpProperties = properties;
                } else if(location.equals("upc.properties")){
                    upcProperties = properties;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("propertiesFile=" + location + "load error !!!");
            }
        }
    }

    public Properties getPropertiesByName(String propertiesName){
        return this.propertiesMap.get(propertiesName);
    }

    public String getRootPropertiesVal(String name){
        return this.rootProperties.getProperty(name);
    }

    public String getHttpPropertiesVal(String name){
        return this.httpProperties.getProperty(name);
    }

    public String getUpcPropertiesVal(String name){
        return this.upcProperties.getProperty(name);
    }
}
