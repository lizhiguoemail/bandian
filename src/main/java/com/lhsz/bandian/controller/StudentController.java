package com.lhsz.bandian.controller;

import com.lhsz.bandian.entity.Student;
import com.lhsz.bandian.service.Impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/findStu")
   public String findStu(){
     Student student= studentService.getById(1);
     return student.getName();
   }
   @PutMapping("/addStu")
   public void addStu(Student student){
        studentService.save(student);
   }
   @DeleteMapping("/remove")
   public void remove(Long id){
        studentService.removeById(id);
   }
}
