package com.lhsz.bandian.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.lhsz.bandian.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_attach")
@ApiModel(value="Attach对象", description="附件")
public class Attach extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件标识")
    @TableId(value = "attach_id", type = IdType.ASSIGN_UUID)
    private String attachId;

    @ApiModelProperty(value = "关联对象标识")
    private String objectId;

    @ApiModelProperty(value = "关联对象类型")
    private String objectType;

    @ApiModelProperty(value = "类型代码")
    private String typeCode;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "附件名称")
    private String attachName;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "MIME类型")
    private String mimeType;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "扩展名")
    private String extensionName;

    @ApiModelProperty(value = "附件路径")
    private String filePath;


}
