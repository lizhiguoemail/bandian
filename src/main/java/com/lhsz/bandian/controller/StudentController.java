package com.lhsz.bandian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.entity.Student;
import com.lhsz.bandian.pojo.page.Result;
import com.lhsz.bandian.service.Impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@RestController
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/findStu")
    @PreAuthorize("hasAuthority('sys:user:view')")
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
   @GetMapping(value = "/list")
    public Result list(){
       List<Student> list = studentService.list();
       Result layuiPageInfo=new Result();
       layuiPageInfo.setData(list);
       return layuiPageInfo;
   }
    @PostMapping(value = "/listQuery")
    public Result listQuery(Student student ){

        List<Student> list = studentService.list(new QueryWrapper<Student>().like("name",student.getName()));
        Result layuiPageInfo=new Result();
        layuiPageInfo.setData(list);
        return layuiPageInfo;
    }
    @GetMapping(value = "/listQuery2")
    public Result listQuery2(String name){

        List<Student> list = studentService.list(new QueryWrapper<Student>().like("name",name));
        Result layuiPageInfo=new Result();
        layuiPageInfo.setData(list);
        return layuiPageInfo;
    }
}
