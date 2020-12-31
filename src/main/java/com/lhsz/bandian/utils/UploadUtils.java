package com.lhsz.bandian.utils;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author lizhiguo
 * 2020/7/16 17:36
 */
@Slf4j
public class UploadUtils {

    /**
     * @param file File
     * @return 附件新名称
     * @throws IOException
     */
    public static String upload(MultipartFile file) throws IOException {
        String newFileName = createNewFileName(file);
        String filePathName=BandianProperties.uploadPath+File.separator + newFileName;
        File dest = new File(filePathName);
        if(!dest.exists()&&!dest.isDirectory()){
            dest.mkdirs();
        }
        file.transferTo(dest);
        log.info("上传成功"+file.getOriginalFilename()+"重名名为："+file.getOriginalFilename());
        return newFileName;
    }
    /**
     * @param file File
     * @param filePath 保存路径
     * @return 附件新名称
     * @throws IOException
     */
    public static String upload(MultipartFile file,String filePath) throws IOException {
        String newFileName = createNewFileName(file);
        String filePathName=filePath+File.separator + newFileName;
        File dest = new File(filePathName);
        if(!dest.exists()&&!dest.isDirectory()){
            dest.mkdirs();
        }
        file.transferTo(dest);
        log.info("上传成功"+file.getOriginalFilename()+"重名名为："+file.getName());
        return newFileName;
    }
    /**
     * 单个上传，上传成功返回附件对象 *为必填
     *
     * @param file       * 上传的文件
     * @param folderName 自定义文件夹名称，没有输入NULL
     * @param isData     .为true时，在目录下每天新建文件夹作为新的保存路径 文件夹格式为:dd-MM-yyyy
     * @return com.lhsz.bandian.sys.entity.Attach
     */
    public static Attach upload(MultipartFile file, String folderName, boolean isData) {
        //验证file是否为空
        if (file.isEmpty()) {
            log.error("上传失败，file is NULL");
            throw new NoticeException("上传失败，file is NULL");
        }
        try {
//      创建目录文件路径
            String filePath = createFilePath(folderName, isData);
            String newName = UploadUtils.upload(file, BandianProperties.uploadPath + File.separator + filePath);
            Attach attach = createAttach(file,filePath + File.separator + newName, newName);
            return attach;
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    private static String createNewFileName(MultipartFile file) {
        return System.currentTimeMillis() + "." + UploadUtils.getExtension(file);
    }
    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return extension;
    }
    public static HttpResult getHttpResult(Attach attach) {
        if(attach == null){
            throw new NoticeException("Attach is null");
        }
        FileUploadDTO result = new FileUploadDTO();
        result.setId(attach.getAttachId());
        result.setUid(attach.getAttachId());
        result.setName(attach.getAttachName());
        result.setFilename(attach.getFileName());
        result.setType(attach.getMimeType());
        result.setSize(attach.getFileSize());
        result.setExtensionName(attach.getExtensionName());
        result.setFilePath(attach.getFilePath());
        result.setUrl(attach.getFilePath());
        result.setTypeCode(attach.getTypeCode());
        result.setTypeName(attach.getTypeName());
        return HttpResult.ok(result);
    }
    public static boolean deleteFileByPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            if(file.delete()){
                log.info("已删除文件：" + file.getPath());
                return true;
            }else{
                log.info("删除文件{}失败：" + file.getPath());
                return false;
            }
        }
        return false;
    }
    public static boolean deleteFileByFile(File file) {
        if (file!=null&&file.exists()) {
            if(file.delete()){
                log.info("已删除文件：" + file.getPath());
                return true;
            }else{
                log.info("删除文件{}失败：" + file.getPath());
                return false;
            }
        }
        return false;
    }

    public static File createFile(String filePathName) {
        File file = new File(filePathName);
        if(file.exists()){
            return file;
        }else{
            throw new NoticeException("文件不存在");
        }
    }
    public static Attach createAttach(MultipartFile file, String filePath, String newName) throws IOException {
        return createAttach(file, filePath, newName,null,null,null,null);
    }
    public static Attach createAttach(MultipartFile file, String filePath, String newName, String objId, String objectType, String typeCode, String typeName) throws IOException {
        Attach attach = new Attach();
        attach.setAttachId(UUID.randomUUID().toString());
        attach.setAttachName(file.getOriginalFilename());
        attach.setFileName(newName);
        attach.setFilePath(filePath);
        attach.setObjectId(objId);
        attach.setObjectType(objectType);
        attach.setMimeType(file.getContentType());
        attach.setFileSize(file.getSize());
        attach.setExtensionName("." + UploadUtils.getExtension(file));//扩展名
        attach.setTypeCode(typeCode);
        attach.setTypeName(typeName);
        return attach;
    }
    public static String createFilePath(String folderName, boolean isData) {
        String separatorChar =  File.separator;
        String filePath = separatorChar + BandianProperties.prefixPath;
        if (folderName != null && !"".equals(BandianProperties.uploadPath.trim())) {
            filePath += separatorChar + folderName;
        }
        if (isData) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            filePath += separatorChar + formatter.format(new Date());
        }
        return filePath;
    }


}
