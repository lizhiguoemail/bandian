package com.lhsz.bandian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Student")
public class Student {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private Long id;


    private String name;

    private Integer age;
    //排除非表中字段
    @TableField(exist=false)
    private String sex;
    //关键字处理
    @TableField(value = "`status`")
    private Boolean status;
}
