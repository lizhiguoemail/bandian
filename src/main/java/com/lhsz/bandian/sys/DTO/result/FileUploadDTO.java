package com.lhsz.bandian.sys.DTO.result;

import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.utils.UploadUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FileUpload对象", description="附件")
public class FileUploadDTO {

    private static final long serialVersionUID = 1L;

    public FileUploadDTO (){}

    public FileUploadDTO(Attach attach) {
        this.setId(attach.getAttachId());
        this.setUid(attach.getAttachId());
        this.setName(attach.getAttachName());
        this.setFilename(attach.getFileName());
        this.setType(attach.getMimeType());
        this.setSize(attach.getFileSize());
        this.setExtensionName(attach.getExtensionName());
        this.setFilePath(attach.getFilePath());
        this.setUrl(attach.getFilePath());
        this.setTypeCode(attach.getTypeCode());
        this.setTypeName(attach.getTypeName());
    }

    @ApiModelProperty(value = "附件标识")
    private String id;

    @ApiModelProperty(value = "附件标识")
    private String uid;

    @ApiModelProperty(value = "附件名称")
    private String name;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "MIME类型")
    private String type;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "扩展名")
    private String extensionName;

    @ApiModelProperty(value = "附件路径")
    private String filePath;

    @ApiModelProperty(value = "附件路径")
    private String url;

    @ApiModelProperty(value = "类型代码")
    private String typeCode;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "类型代码")
    private String objectId;

    @ApiModelProperty(value = "类型名称")
    private String objectType;

    public static Attach ToAttach(FileUploadDTO dto){
        Attach attach = new Attach();
        attach.setAttachId(dto.getId());
        attach.setAttachName(dto.getName());
        attach.setFileName(dto.getFilename());
        attach.setFilePath(dto.getFilePath());
        attach.setObjectId(dto.getObjectId());
        attach.setObjectType(dto.getObjectType());
        attach.setMimeType(dto.getType());
        attach.setFileSize(dto.getSize());
        attach.setExtensionName(dto.getExtensionName());//扩展名
        attach.setTypeCode(dto.getTypeCode());
        attach.setTypeName(dto.getTypeName());
        return attach;
    }
}
