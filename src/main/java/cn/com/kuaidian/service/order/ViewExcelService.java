package cn.com.kuaidian.service.order;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.com.kuaidian.entity.Order;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcelService extends AbstractExcelView {

    private List<Order> orderList = null;
    
    public void setOrderList(List<Order> list) {
        this.orderList = list;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> obj, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HSSFSheet sheet = workbook.createSheet("线索量");
        sheet.setDefaultColumnWidth((short) 12);

        int rownum = 0;
        if (orderList != null && orderList.size() > 0) {
        	HSSFRow sheetRow1 = sheet.createRow(rownum);
            sheetRow1.createCell((short) 0).setCellValue(new HSSFRichTextString("名单线索量表ID"));
            sheetRow1.createCell((short) 1).setCellValue(new HSSFRichTextString("姓名"));
            sheetRow1.createCell((short) 2).setCellValue(new HSSFRichTextString("手机号"));
            sheetRow1.createCell((short) 3).setCellValue(new HSSFRichTextString("经销商ID"));
            sheetRow1.createCell((short) 4).setCellValue(new HSSFRichTextString("城市ID"));
            sheetRow1.createCell((short) 5).setCellValue(new HSSFRichTextString("车系ID"));
            sheetRow1.createCell((short) 6).setCellValue(new HSSFRichTextString("平台来源"));
            sheetRow1.createCell((short) 7).setCellValue(new HSSFRichTextString("页面来源"));
            sheetRow1.createCell((short) 8).setCellValue(new HSSFRichTextString("创建时间"));
            for (Order order : orderList) {
        		HSSFRow sheetRow2 = sheet.createRow(rownum + 1);
        		String createTime = "";
        		if(order.getCreateTime() != null){
        			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss ");
        			createTime = formatter.format(order.getCreateTime());//格式化数据
        		}
                sheetRow2.createCell((short) 0).setCellValue(new HSSFRichTextString(order.getId()+""));
                if(order.getName()!=null){
                	sheetRow2.createCell((short) 1).setCellValue(new HSSFRichTextString(order.getName()));
                }
                if(order.getMobile()!=null){
                	sheetRow2.createCell((short) 2).setCellValue(new HSSFRichTextString(order.getMobile()));
                }
                if((Long)order.getDealerId()!=null){
                	sheetRow2.createCell((short) 3).setCellValue(new HSSFRichTextString(order.getDealerId()+""));
                }
                if((Long)order.getCityId()!=null){
                	sheetRow2.createCell((short) 4).setCellValue(new HSSFRichTextString(order.getCityId()+""));
                }
                if((Long)order.getSerialGroupId()!=null){
                	sheetRow2.createCell((short) 5).setCellValue(new HSSFRichTextString(order.getSerialGroupId()+""));
                }
                if((Integer)order.getOrigin()!=null){
                	sheetRow2.createCell((short) 6).setCellValue(new HSSFRichTextString(order.getOrigin()+""));
                }
                if(order.getRefer()!=null){
                	sheetRow2.createCell((short) 7).setCellValue(new HSSFRichTextString(order.getRefer()));
                }
                if(createTime!=null){
                	sheetRow2.createCell((short) 8).setCellValue(new HSSFRichTextString(createTime));
                }
                
                rownum++;
        
            }
        }

        String filename = "导出线索量.xls";//设置下载时客户端Excel的名称
        filename = encodeFilename(filename, request);//处理中文文件名
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }


    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }
}
