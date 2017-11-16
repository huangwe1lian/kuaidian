package cn.com.fawtoyota.service.admin;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderCodeService {
	@Autowired
	GeliDao geliDao;
	
	/**
	 * 获取线索量授权代码
	 * @return
	 */
	public String createOrderCode(int length){
	        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
	        Random random = new Random();     
	        StringBuffer sb = new StringBuffer();  
	        Format format = new SimpleDateFormat("yyyy-MMdd");
	        sb.append( format.format(new Date()) ).append("-");
	        
	        for (int i = 0; i < length; i++) {     
	            int number = random.nextInt(base.length());     
	            sb.append(base.charAt(number));     
	        }
	        sb.append("-");
	        for (int i = 0; i < length; i++) {     
	            int number = random.nextInt(base.length());     
	            sb.append(base.charAt(number));     
	        }
	        return sb.toString();     
	}
	
	
}
