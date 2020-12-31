package com.lhsz.bandian.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lizhiguo
 * @Date 2020/12/25 16:50
 */
@Controller
@RequestMapping("/")
public class SwaggerController {
    @GetMapping()
    public String index()
    {
        return "redirect:/doc.html";
//        return "redirect:/swagger-ui.html";
    }
}
