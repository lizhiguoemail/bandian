package com.lhsz.bandian.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhusenlin
 * Date on 2020/8/10  8:49
 */
public class PDFUtils {
    private static final Logger log = LoggerFactory.getLogger(PDFUtils.class);

    /**
     * 根据pdf模板输出流
     * @param templateFilePath 模板目录路径
     * @param templateFileName 模板文件名
     * @param resultMap 包含文件字段名和值的map
     * @return 生成的文件字节流
     */
    public static ByteArrayOutputStream createPdfStream(String templateFilePath, String templateFileName,
                                                        Map<String, String> resultMap) throws IOException, DocumentException {

        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PdfStamper stamp =null;
        PdfReader reader = null;
        try {
            log.debug("file:"+templateFilePath + "/" + templateFileName);
            reader = new PdfReader(templateFilePath + "/" + templateFileName);
            stamp = new PdfStamper(reader, ba);

            //使用字体
            BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            /* 获取模版中的字段 */
            AcroFields form = stamp.getAcroFields();

            //填充表单
            if (resultMap != null) {
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    form.setFieldProperty(entry.getKey(), "textfont", bf, null);
                    form.setField(entry.getKey(), entry.getValue());
                }
            }
            //不能编辑
            stamp.setFormFlattening(true);
        }finally {
            if(stamp!=null){
                stamp.close();
            }
            if(reader!=null){
                reader.close();
            }
        }
        return ba;
    }

    /**
     * 根据 pdf 模板(带一张图)输出流
     * @param templateFilePath 模板目录路径
     * @param templateFileName 模板文件名
     * @param resultMap 包含文件字段名和值的map
     * @return 生成的文件字节流
     */
    public static ByteArrayOutputStream createPdfStream(String templateFilePath, String templateFileName,
                                                        Map<String, String> resultMap,String imagePath) throws IOException, DocumentException {

        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PdfStamper stamp =null;
        PdfReader reader = null;
        try {

            log.debug("file:"+templateFilePath + "/" + templateFileName);
            reader = new PdfReader(templateFilePath + "/" + templateFileName);
            stamp = new PdfStamper(reader, ba);

            //使用字体
            BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            /* 获取模版中的字段 */
            AcroFields form = stamp.getAcroFields();

            //填充表单
            if (resultMap != null) {
                for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                    form.setFieldProperty(entry.getKey(), "textfont", bf, null);
                    form.setField(entry.getKey(), entry.getValue());
                }
            }
            //获取源pdf的总页数
            int pages = reader.getNumberOfPages();
            //指定在最后一页插入图片
            PdfContentByte overContent = stamp.getOverContent(pages);
            //添加图片
            Image image = Image.getInstance(imagePath);//图片名称
            image.scaleAbsolute(100, 151);//图片大小
            image.setAbsolutePosition(418, 396);//左边距、底边距
            overContent.addImage(image);
            overContent.stroke();

            //不能编辑
            stamp.setFormFlattening(true);
        }finally {
            if(stamp!=null){
                stamp.close();
            }
            if(reader!=null){
                reader.close();
            }
        }
        return ba;
    }
}
