package com.lhsz.bandian.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    @TableField(value = "creation_time", fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

    /**
     * 创建人编号
     */
    private Long creatorId;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_modification_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModificationTime;

    /**
     * 最后修改人编号
     */
    private Long lastModifierId;

    /**
     * 是否删除
     */
    @TableLogic(value = "true",delval = "false")
    private Boolean isDeleted;

    /**
     * 版本号
     */
    @Version
    private Integer version;

}
