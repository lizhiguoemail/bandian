package com.lhsz.bandian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Student")
public class Student {
    @TableId(value = "id",type = IdType.UUID)
    private Long id;


    private String name;

    private Integer age;

    private String sex;
}
