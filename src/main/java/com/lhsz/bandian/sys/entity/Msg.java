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
 * 系统消息
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_msg")
@ApiModel(value="Msg对象", description="系统消息")
public class Msg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息标识")
    @TableId(value = "msg_id", type = IdType.UUID)
    private String msgId;

    @ApiModelProperty(value = "消息名称")
    private String msgName;

    @ApiModelProperty(value = "消息标题")
    private String msgTitle;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "消息内容")
    private String msgBody;

    @ApiModelProperty(value = "排序")
    private Integer sortId;


}
