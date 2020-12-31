package com.lhsz.bandian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.entity.Student;
import com.lhsz.bandian.mapper.StudentMapper;
import com.lhsz.bandian.service.IStudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
