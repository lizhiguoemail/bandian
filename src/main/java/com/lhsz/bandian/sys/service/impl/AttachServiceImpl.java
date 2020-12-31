package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.mapper.AttachMapper;
import com.lhsz.bandian.sys.service.IAttachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 附件 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Slf4j
@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements IAttachService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IUserService userService;
    public static final long DEFAULT_MAX_SIZE = 200 * 1024;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Attach uploadAvatar(MultipartFile file) {
        User user = tokenService.getLoginUserToUser();
        if (file.getSize() > DEFAULT_MAX_SIZE) {
            log.error("头像像素不能大于200KB" + DEFAULT_MAX_SIZE);
            throw new NoticeException("头像像素不能大于200KB");
        }
        Attach attach = upload(file, "avatar" + File.separatorChar + user.getUserName(), false, user.getUserId(), "用户头像", null, null);

        tokenService.updateLoginUserToUser();
        return attach;
    }

    @Override
    public Attach upload(MultipartFile file) {
        return upload(file, null);
    }

    @Override
    public Attach upload(MultipartFile file, String objectId) {
        return upload(file, objectId, null);
    }

    @Override
    public Attach upload(MultipartFile file, String objectId, String typeCode) {
        return this.upload(file, null, true, objectId, null, typeCode, null);
    }

    @Override
    public Attach upload(MultipartFile file, String objectId, String typeCode, String typeName) {
        return this.upload(file, null, true, objectId, null, typeCode, typeName);
    }

    /**
     * 单个上传，上传成功返回附件对象 *为必填
     *
     * @param file       * 上传的文件
     * @param folderName 自定义文件夹名称，没有输入NULL
     * @param isData     .为true时，在目录下每天新建文件夹作为新的保存路径 文件夹格式为:dd-MM-yyyy
     * @param objectId   * 关联对象标识，一般是数据ID
     * @param objectType 关联对象类型 增加可读性，例如：资质证明材料
     * @param typeCode   类型代码 一条记录多个附件时可以使用此字段区分，例如：1
     * @param typeName   类型名称 可以命名增加可读性 例如：身份证正面
     * @return com.lhsz.bandian.sys.entity.Attach
     */
    @Override
    public Attach upload(MultipartFile file, String folderName, boolean isData, String objectId, String objectType, String typeCode, String typeName) {
        //验证file是否为空
        if (file.isEmpty()) {
            log.error("上传失败，file is NULL");
            throw new NoticeException("上传失败，file is NULL");
        }
        try {
//      创建目录文件路径
            String filePath = UploadUtils.createFilePath(folderName, isData);
            String newName = UploadUtils.upload(file, BandianProperties.uploadPath + File.separator + filePath);
            Attach attach = UploadUtils.createAttach(file, filePath + File.separator + newName, newName, objectId, objectType, typeCode, typeName);

            if (objectId != null && !"".equals(objectId.trim())) {
                deleteAttach(attach);
                save(attach);
            }
            return attach;
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public List<Attach> upload(MultipartFile[] files) {
        return upload(files, null);
    }

    @Override
    public List<Attach> upload(MultipartFile[] files, String objectId) {
        return upload(files, objectId, null);
    }

    @Override
    public List<Attach> upload(MultipartFile[] files, String objectId, String typeCode) {
        return this.upload(files, null, true, objectId, null, typeCode, null);
    }

    @Override
    public List<Attach> upload(MultipartFile[] files, String objectId, String typeCode, String typeName) {
        return this.upload(files, null, true, objectId, null, typeCode, typeName);
    }

    @Override
    public List<Attach> upload(MultipartFile[] files, String folderName, boolean isData, String objectId, String objectType, String typeCode, String typeName) {
        /*if (objectId.isEmpty()) {
            log.error("上传失败，参数错误：objectId is NULL");
            throw new NoticeException("上传失败，参数错误：objectId is NULL");
        }*/
        if (files != null && files.length > 0) {
            List<Attach> list = new ArrayList<>();
            for (MultipartFile file : files) {
                Attach attach = upload(file, folderName, isData, objectId, objectType, typeCode, typeName);
                list.add(attach);
            }
             /*   //验证file是否为空
                if (file.isEmpty()) {
                    log.error("上传失败，file is NULL");
                    continue;
                }

                try {
                    //创建目录文件路径
                    String filePath=createFilePath(folderName,isData);
                    String newName=createNewFileName(file);
                    Attach attach=createAttach(file,filePath+File.separatorChar+newName,objectId,objectType,typeCode,typeName);
                    UploadUtils.upload(file,filePath+File.separatorChar+newName);

                    list.add(attach);
                } catch (IOException e) {
                    log.error(e.toString(), e);
                }*/

            return list;
        }
        return null;
    }

    @Override
    public boolean saveAttach(Attach attach) {
        return super.save(attach);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean saveOrUpdateAttach(Attach attach) {
//        deleteAttach(attach);
        return saveOrUpdate(attach);
    }

    @Override
    public void deleteAttach(Attach attach) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("object_id", attach.getObjectId());
        if (attach.getTypeCode() != null) {
            queryWrapper.eq("type_code", attach.getTypeCode());
        }
        List list = list(queryWrapper);
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            Attach attach1 = (Attach) list.get(i);
            deleteFile(BandianProperties.uploadPath + attach1.getFilePath());
            ids.add(attach1.getAttachId());
        }
        if (ids.size() > 0) {
            removeByIds(ids);
        }

    }

    @Override
    public boolean saveAttachBatch(List<Attach> attachList) {
        return saveBatch(attachList);
    }

    @Override
    public boolean saveOrUpdateAttachBatch(List<Attach> attachList) {
        return saveOrUpdateBatch(attachList);
    }

    @Override
    public boolean deleleteByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids);
    }

    private boolean deleteFile(String path) {
//        File file = new File(path);
//        if (file.exists()) {
//            log.info("已删除文件：" + file.getPath());
//            return file.delete();
//        }
        return UploadUtils.deleteFileByPath(path);
    }
    @Override
    public List<FileUploadDTO> listByObjectId(String objectId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("object_id", objectId);
        List<Attach> list = list(queryWrapper);
        List<FileUploadDTO> result = new ArrayList<>();
        list.forEach(obj->result.add(new FileUploadDTO(obj)));
        return result;
    }
}
