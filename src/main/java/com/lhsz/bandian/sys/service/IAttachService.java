package com.lhsz.bandian.sys.service;

import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 附件 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
public interface IAttachService extends IService<Attach> {

    Attach uploadAvatar(MultipartFile file);

    /**
     * 上传，不会保存到数据库
     * 根据返回的Attach对象自己添加objectId之后在调用saveAttach方法进行数据保存
     * @param file
     * @return Attach
     */
    Attach upload(MultipartFile file); /**
     * 上传
     * 并保存到数据库
     * @param file
     * @param objectId 数据ID
     * @return
     */
    Attach upload(MultipartFile file,String objectId);

    /**
     * 上传
     * 并保存到数据库
     * @param file
     * @param objectId 数据ID
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @return
     */
    Attach upload(MultipartFile file,String objectId,String typeCode);

    /**
     * 上传
     * 并保存到数据库
     * @param file
     * @param objectId 数据ID
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @param typeName 类型名称 一条记录多个附件时可以使用此字段区分，例如：1
     * @return
     */
    Attach upload(MultipartFile file,String objectId,String typeCode, String typeName);
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
    Attach upload(MultipartFile file,String folderName,boolean isData,String objectId,String objectType,String typeCode,String typeName);
    /**
     * 批量上传，不会保存到数据库
     * @param files
     * @return
     */
    List<Attach> upload(MultipartFile[] files);
    /**
     * 批量上传
     * 并保存到数据库
     * @param files
     * @param objectId 数据ID
     * @return
     */
    List<Attach> upload(MultipartFile[] files,String objectId);

    /**
     * 批量上传
     * 并保存到数据库
     * @param files
     * @param objectId 数据ID
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @return
     */
    List<Attach> upload(MultipartFile[] files,String objectId,String typeCode);

    /**
     * 批量上传
     * 并保存到数据库
     * @param files
     * @param objectId 数据ID
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @param typeName 类型名称 一条记录多个附件时可以使用此字段区分，例如：1
     * @return
     */
    List<Attach> upload(MultipartFile[] files,String objectId,String typeCode,String typeName);
    /**
     *  批量上传，并保存到数据库，上传成功返回附件对象 *为必填
     *
     * @param files * 上传的文件集合
     * @param folderName 自定义文件夹名称，没有输入NULL
     * @param isData .为true时，在目录下每天新建文件夹作为新的保存路径 文件夹格式为:dd-MM-yyyy
     * @param objectId * 关联对象标识，一般是数据ID
     * @param objectType 关联对象类型 增加可读性，例如：资质证明材料
     * @param typeCode 类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @param typeName 类型名称 可以命名增加可读性 例如：身份证正面
     * @return  List<Attach> com.lhsz.bandian.sys.entity.Attach
     */
    List<Attach> upload(MultipartFile[] files,String folderName,boolean isData,String objectId,String objectType,String typeCode,String typeName);

    boolean saveAttach(Attach attach);
    boolean saveOrUpdateAttach(Attach attach);
    void deleteAttach(Attach attach);
    boolean saveAttachBatch(List<Attach> attachList);
    boolean saveOrUpdateAttachBatch(List<Attach> attachList);

    /**
     * 批量删除
     * @param ids 附件ID集合
     * @return
     */
    boolean deleleteByIds(Collection<? extends Serializable> ids);

    /**
     * 根据ObjectId查询列表
     * @param objectId
     * @return
     */
    List<FileUploadDTO> listByObjectId(String objectId);
}
