package com.lhsz.bandian.mapper;

import com.lhsz.bandian.entity.Student;

public interface StudentMapper extends MyBaseMapper<Student> {
    @Override
    int insert(Student student);

}
