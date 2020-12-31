package com.lhsz.bandian.portal.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JuryDeclareSubjectDTO;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.entity.JuryDeclareSubject;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.jt.mapper.JuryDeclareSubjectMapper;
import com.lhsz.bandian.jt.service.IJuryDeclareSubjectService;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评委会主管部门 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController("juryDept")
@RequestMapping("/portal/jt/jury-dept")
public class JuryController extends BaseController {

    private final IJuryDeptService juryDeptService;
    private final IJuryDeclareSubjectService juryDeclareSubjectService;

    @Autowired
    public JuryController(IJuryDeptService juryDeptService,IJuryDeclareSubjectService juryDeclareSubjectService) {
        this.juryDeptService = juryDeptService;
        this.juryDeclareSubjectService=juryDeclareSubjectService;
    }

    /**
     * 查询评委会主管部门
     */
    @GetMapping()
    public HttpResult page(JuryDeptDTO juryDeptDTO) {
        startPage();
        ArrayList<String> strings = new ArrayList<>();
        strings.add(juryDeptDTO.getProvince());
        strings.add(juryDeptDTO.getCounty());
        strings.add(juryDeptDTO.getCity());
        juryDeptDTO.setCitys(strings);
        return HttpResult.ok(getDataTable(juryDeptService.list(juryDeptDTO)));
    }

    /**
     * 查询评委会主管部门
     */
    @GetMapping("/list")
    public HttpResult list(JuryDeptDTO juryDeptDTO) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(juryDeptDTO.getProvince());
        strings.add(juryDeptDTO.getCounty());
        strings.add(juryDeptDTO.getCity());
        juryDeptDTO.setCitys(strings);
        return HttpResult.ok(juryDeptService.list(juryDeptDTO));
    }



    /**
     * 根据declareCategory搜索评委会
     *
     * @param
     * @return
     */
    @GetMapping("/search")
    public HttpResult search( JuryDeclareSubjectDTO juryDeclareSubjectDTO) {
        try {
            List<JuryDeptDTO> juryDeclarePlanDTO = juryDeclareSubjectService.search(juryDeclareSubjectDTO);
            return HttpResult.ok(juryDeclarePlanDTO);
        } catch (Exception e) {
            log.debug("获取评委会申报计划异常",e);
            return HttpResult.error("系统内部异常");
        }
    }


    /**
     * 评委会主管部门详情
     *
     * @param id 部门详情id
     * @return 部门详情
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        JuryDeptDTO juryDeptDTO = juryDeptService.selectById(id);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(juryDeptDTO.getProvince());
        strings.add(juryDeptDTO.getCounty());
        strings.add(juryDeptDTO.getCity());
        juryDeptDTO.setCitys(strings);
        return HttpResult.ok(juryDeptDTO);
    }

    /**
     * 查询系列类别
     */
    @ApiOperation(value = "根据类别ID查询该类别下的专业")
    @ApiImplicitParam(name = "categoryId", value = "类别ID", dataType = "String")
    @GetMapping("/all/{deptType}")
    public HttpResult listAllByCategoryId(@PathVariable("deptType") Integer deptType) {
            List<SelectDTO> result = juryDeptService.listAllBydeptType(deptType);
        return HttpResult.ok(result);
    }
}
