package com.lhsz.bandian.sys.DTO.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lizhiguo
 * 2020/7/14 17:18
 */
@Data
public class BaseDTO implements Serializable {
    @ApiModelProperty(value = "唯一标识")
    private String id;
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建时间
     */
    private LocalDateTime creationTime;

    /**
     * 创建人编号
     */
    private String creatorId;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModificationTime;

    /**
     * 最后修改人编号
     */
    private String lastModifierId;
    private Integer version;
}
