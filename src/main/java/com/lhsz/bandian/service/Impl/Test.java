package com.lhsz.bandian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.entity.Student;
import com.lhsz.bandian.mapper.MyBaseMapper;
import com.lhsz.bandian.mapper.StudentMapper;
import com.lhsz.bandian.service.ITest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lizhiguo
 * 2020/7/10 15:06
 */
public class Test extends ServiceImpl<StudentMapper,Student> implements ITest {
    @Autowired
    MyBaseMapper myBaseMapper;
    public void  Test(){

    }
}
