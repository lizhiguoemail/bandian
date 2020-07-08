package com.lhsz.bandian.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @TableField(value = "creation_time", fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

    /**
     * 创建人编号
     */
    @TableField(value = "creator_id", fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_modification_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModificationTime;

    /**
     * 最后修改人编号
     */
    @TableField(value = "last_modifier_id", fill = FieldFill.INSERT_UPDATE)
    private Long lastModifierId;

    /**
     * 是否删除
     */
    @TableLogic(value = "0",delval = "1")
    private Boolean isDeleted;

    /**
     * 版本号
     */
    @Version
    private Integer version;

}
