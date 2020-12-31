package com.lhsz.bandian.sys.DTO.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lizhiguo
 * 2020/7/14 17:18
 */
@Data
@Accessors(chain = true)
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "唯一标识")
    private String id;
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime creationTime;

    /**
     * 创建人编号
     */
    private String creatorId;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastModificationTime;

    /**
     * 最后修改人编号
     */
    private String lastModifierId;

    private Integer version;
    private Integer isDeleted;

}
