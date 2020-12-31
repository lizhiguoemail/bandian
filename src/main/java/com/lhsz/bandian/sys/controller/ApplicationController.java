package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.result.ApplicationDTO;
import com.lhsz.bandian.sys.service.IApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 应用程序 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/systems/application")
@Api(tags = "应用程序 前端控制器")
public class ApplicationController extends BaseController {

    @Autowired
    private IApplicationService applicationService;
    @GetMapping("/all")
    public HttpResult all(){
        List<ApplicationDTO> list=new ArrayList<>();
        applicationService.list().forEach(app->{
            ApplicationDTO applicationDTO=new ApplicationDTO();
            BeanUtils.copyProperties(app,applicationDTO);
            applicationDTO.setId(app.getApplicationId());
            list.add(applicationDTO);
        });
        return HttpResult.ok(list);
    }

}
