package com.lhsz.bandian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.entity.Student;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.page.Result;
import com.lhsz.bandian.service.Impl.StudentService;
import com.lhsz.bandian.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HttpResult listQuery(Student student ){

        List<Student> list = studentService.list(new QueryWrapper<Student>().like("name",student.getName()));

        return HttpResult.ok(list);
    }
    @GetMapping(value = "/listQuery2")
    public HttpResult listQuery2(String name){

        QueryWrapper qe=new QueryWrapper();
//        Map aa=new HashMap();
//        if(StringUtils.isNotEmpty(name))
//        aa.put("name",name);
//        aa.put("age",50);
        if(StringUtils.isNotEmpty(name))
        qe.like("name",name);
        qe.like("age",50);
        List<Student> list = studentService.list(qe);
        return HttpResult.ok(list);
    }

}
