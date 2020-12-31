package com.lhsz.bandian.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhusenlin
 * Date on 2020/9/24  11:26
 */
@Data
@EqualsAndHashCode()
@ApiModel(value="EmailMsg对象", description="邮件数据")
public class EmailMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收件人")
    private String toEmailAddress;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @Override
    public String toString() {
        return toEmailAddress + ',' + verifyCode;
    }
}
