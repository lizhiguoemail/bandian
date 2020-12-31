package com.lhsz.bandian.sys.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author lizhiguo
 * @Date 2020/12/24 9:16
 */
@Data
public class UploadDTO implements Serializable {
    /**
     *  单个上传，上传成功返回附件对象 *为必填
     * @param file * 上传的文件
     * @param folderName 自定义文件夹名称，没有输入NULL
     * @param isData .为true时，在目录下每天新建文件夹作为新的保存路径 文件夹格式为:dd-MM-yyyy
     * @param objectId * 关联对象标识，一般是数据ID
     * @param objectType 关联对象类型 增加可读性，例如：资质证明材料
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @param typeName 类型名称 可以命名增加可读性 例如：身份证正面
     * @return com.lhsz.bandian.sys.entity.Attach
     */
    /**
     * 上传文件
     */
    private MultipartFile file;
    /**
     * 存放的文件夹地址
     */
    private String folderName;
    /**
     * 是否每日创建新的目录
     */
    private Boolean isData;
}
