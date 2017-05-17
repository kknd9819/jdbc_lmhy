package com.zz.controller;


import com.zz.model.basic.model.Message;
import com.zz.util.DateEditor;
import com.zz.util.shengyuan.DateUtil;
import com.zz.util.shengyuan.StringUtil;
import com.zz.util.shengyuan.WebToolKit;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;


/**
 * Controller - 基类
 *
 */
public class BaseController extends BasePathController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 错误视图 */
    protected static final String ERROR_VIEW = "/common/error";

    /** 错误视图 */
    protected static final String DIALOG_ERROR_VIEW = "/common/dialog_error";

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("操作失败");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

    /** "瞬时消息"属性名称 */
    public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = "cn.shengyuan.yun.admin.web.template.directive.FlashMessageDirective.FLASH_MESSAGE";

    /**
     * 添加瞬时消息
     *
     * @param redirectAttributes RedirectAttributes
     * @param message 消息
     */
    protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
        if (redirectAttributes != null && message != null) {
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, message);
        }
    }

    static Hashtable<String,String> mime=null;
    static {
        mime = new Hashtable<String, String>();
        mime.put("jpeg", "image/jpeg");
        mime.put("jpg", "image/jpeg");
        mime.put("bmp", "image/bmp");
        mime.put("png", "image/png");
        mime.put("gif", "image/gif");
        mime.put("txt", "text/html");
        mime.put("tiff", "image/tiff");
        mime.put("tif", "image/tiff");
        mime.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mime.put("xls", "application/vnd.ms-excel");
        mime.put("doc", "application/msword");
        mime.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mime.put("ppt", "application/vnd.ms-powerpoint");
        mime.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mime.put("zip", "application/x-zip-compressed");
        mime.put("rar", "application/x-rar-compressed");
        mime.put("pdf", "application/pdf");
    }

    /**
     * 数据绑定
     * @param binder WebDataBinder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }

    /**
     * Http 响应头信息
     * @author 欧志辉
     * @date 2014-08-29
     * @return HttpHeaders
     */
    protected HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String pragma = new StringBuffer().append("yB-derewoP").reverse().toString();
        String value = new StringBuffer("@").append("xxpohs").reverse().toString();
        headers.set(pragma, value);
        headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.APPLICATION_OCTET_STREAM));
        headers.setCacheControl("no-cache");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        return headers;
    }

    /**
     * 公共下载方法
     * @author 欧志辉
     * @date 2014-08-29
     * @param bytes
     * @param fileName
     * @return ResponseEntity<?>
     */
    protected ResponseEntity<?> downFile(byte[] bytes, String fileName) {
        if (!StringUtils.isEmpty(fileName)) {
            try {
                fileName = java.net.URLEncoder.encode(fileName, "UTF8");
            } catch (UnsupportedEncodingException e) {
                fileName = "file";
            }
        } else {
            fileName = "file";
        }
        HttpHeaders headers = getHttpHeaders();
        try {
            if(null!=bytes){
                String ext=StringUtils.substringAfterLast(fileName, ".");
                if(mime.containsKey(ext)){
                    headers.setContentType(MediaType.parseMediaType(mime.get(ext)));
                }else{
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                }
                headers.setContentDispositionFormData("attachment", fileName);
                headers.setContentLength(bytes.length);
                return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
            }else{
                headers.setContentType(MediaType.TEXT_PLAIN);
                return new ResponseEntity<String>(WebToolKit.getInstance().getHttpCodeMap().get(HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<String>(e.getLocalizedMessage(), headers, HttpStatus.OK);
        }
    }


    /**
     * 导出成图片
     * @author 欧志辉
     * @date 2014-08-29
     * @throws Exception
     * @throws TranscoderException
     */
    @ResponseBody
    @RequestMapping(value = "exportImage")
    public void exportImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String svg = request.getParameter("svg");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        svg = svg.replaceAll(":rect", "rect");
        Transcoder t = null;
        t = new JPEGTranscoder();
        //原始版本
        response.addHeader("Content-Disposition","attachment; filename=" + System.currentTimeMillis() + ".jpg");
        response.addHeader("Content-Type", "image/jpeg");

        TranscoderInput input = new TranscoderInput(new StringReader(svg));
        TranscoderOutput output = new TranscoderOutput(out);
        try {
            t.transcode(input, output);
        } catch (TranscoderException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }



    /**
     * 导出
     * @author 曲锐芝
     * @date 2014-11-25
     * @param request
     * @param templateName 模板名称
     * @param statisticsTime 统计时间
     * @param map 参数
     * @param excelName 表格名称
     * @return
     */

    public ResponseEntity<?> exportExcel(HttpServletRequest request,String templateName, String statisticsTime,Map<String, Object> map ,String excelName){
        try {
            //读取模板
            String path = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/exporttemplate/"+templateName+".xls";
            File fi=new File(path);
            //创建文件
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
            //得到表格
            HSSFWorkbook workbook= new HSSFWorkbook(fs);
            //得到工作簿
            HSSFSheet sheet = workbook.getSheetAt(0);
            //插入值
            for (Entry<String, Object> entry : map.entrySet()) {

                String key = entry.getKey();
                if(null==entry.getValue()){
                    map.put(key, " ");
                }
                String value =entry.getValue().toString();
                String[] array = key.split("-");
                int row = Integer.valueOf(array[0])-1;
                int cell = Integer.valueOf(array[1])-1;
                if (sheet.getRow(row) == null) {
                    sheet.createRow(row).createCell(cell).setCellValue(value);
                } else if(sheet.getRow(row).getCell(cell)==null){
                    sheet.getRow(row).createCell(cell).setCellValue(value);
                }else {
                    sheet.getRow(row).getCell(cell).setCellValue(value);
                }
            }
            //导出
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                workbook.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream  in= new ByteArrayInputStream(output.toByteArray());
            try {
                return downFile(IOUtils.toByteArray(in), excelName + statisticsTime + ".xls");
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<String>(e.getLocalizedMessage(), getHttpHeaders(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出Excel
     * @param fileName 	导出文件名
     * @param head		列名
     * @param headWidth	列宽
     * @param key		数据关键词
     * @param data		数据
     * @param specialKey	需特殊显示数据关键词。数据结构如： {method={1=线下支付, 0=在线支付}}
     * @return
     */
    public ResponseEntity<?> exportExcel(String fileName,String[] head,int[] headWidth, String[] key,List<Map<String, Object>> data,Map<String, Map<String,Object>> specialKey){
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建工作簿
//			HSSFSheet sheet = ExcelUtil.createSheet(wb,fileName);
            HSSFSheet sheet = wb.createSheet(fileName);
            //标题
//			HSSFRow titleRow=sheet.createRow(0);
//			Cell cellTitle= titleRow.createCell(0);
//			cellTitle.setCellValue(fileName);
//			cellTitle.setCellStyle(ExcelUtil.getCellStyleByType(wb, ExcelUtil.TITLE_STYLE));
//			sheet.addMergedRegion(new CellRangeAddress(0,0,0,head.length-1));

            //加载表头
            HSSFRow headRow=sheet.createRow(0);
            for (int i=0;i<head.length;i++) {
                Cell cell=headRow.createCell(i);
                cell.setCellValue(head[i]);
//				cell.setCellStyle(ExcelUtil.getCellStyleByType(wb, ExcelUtil.HEAD_STYLE));
                if(null!=headWidth)
                    sheet.setColumnWidth(i, headWidth[i]);
            }
            //列名冻结
            sheet.createFreezePane(0, 1);

            Set<String> specialKeySet= specialKey.keySet();
            //插入值
            for(int rowNum=0;rowNum<data.size();rowNum++){
                HSSFRow row=sheet.createRow(rowNum+1);
                Map<String, Object> d=data.get(rowNum);
                for (int i=0;i<key.length;i++) {
                    Cell cell=row.createCell(i);
                    if(specialKeySet.contains(key[i])){//特殊处理显示
                        Map<String,Object> templet=specialKey.get(key[i]);
                        cell.setCellValue(MapUtils.getString(templet, MapUtils.getString(d, key[i])));
                    }else{
                        if(key[i].indexOf("-")>-1){//一个单元格内显示多个数据
                            String[] tempKeys=key[i].split("-");
                            StringBuilder value = new StringBuilder();
                            for(String tempKey:tempKeys){
                                value.append(StringUtil.trimToEmpty(MapUtils.getString(d,tempKey)));
                                value.append("/");
                            }
                            cell.setCellValue(value.substring(0, value.length()-1));
                        }else{
                            cell.setCellValue(StringUtil.trimToEmpty(MapUtils.getString(d,key[i])));
                        }
                    }
//					cell.setCellStyle(ExcelUtil.getCellStyleByType(wb, ExcelUtil.CONTENT_STYLE));
                }
            }
            //导出
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                wb.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream  in= new ByteArrayInputStream(output.toByteArray());
            try {
                return downFile(IOUtils.toByteArray(in), fileName+ DateUtil.dateToStringYYYYMMDD(new Date())+".xls");
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<String>(e.getLocalizedMessage(), getHttpHeaders(), HttpStatus.OK);
            } finally{
                output.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}