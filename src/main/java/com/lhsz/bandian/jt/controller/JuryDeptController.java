package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JuryDeptDTO;
import com.lhsz.bandian.jt.entity.JuryDept;
import com.lhsz.bandian.jt.service.IJuryDeptService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
@RestController
@RequestMapping("/jt/jury-dept")
public class JuryDeptController extends BaseController {

    private final IJuryDeptService juryDeptService;
    public final IJuryDeptService iJuryDeptService;
    @Autowired
    public JuryDeptController(IJuryDeptService juryDeptService, IJuryDeptService iJuryDeptService) {
        this.juryDeptService = juryDeptService;
        this.iJuryDeptService = iJuryDeptService;
    }

    /**
     * 查询评委会主管部门
     */
    @GetMapping()
    public HttpResult page(JuryDeptDTO juryDeptDTO) {
        startPage();
        ArrayList<String> strings = new ArrayList<>();
        strings.add(juryDeptDTO.getProvince());
        strings.add(juryDeptDTO.getCity());
        strings.add(juryDeptDTO.getCounty());
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
        strings.add(juryDeptDTO.getCity());
        strings.add(juryDeptDTO.getCounty());
        juryDeptDTO.setCitys(strings);
        return HttpResult.ok(juryDeptService.list(juryDeptDTO));
    }

    /**
     * 查询评委会主管部门
     */
    @GetMapping("/listDisable")
    public HttpResult listDisable(JuryDeptDTO juryDeptDTO) {
        JuryDept juryDept = new JuryDept();
        BeanUtils.copyProperties(juryDeptDTO,juryDept);
        return HttpResult.ok(juryDeptService.listDisableDTO(juryDept));
    }

    /**
     * 添加评委会主管部门
     */
    @PostMapping()
    public HttpResult add(@RequestBody JuryDeptDTO juryDept){
        // 普通用户不能访问该接口
        if(juryDept.getDeptType()==null){
            return HttpResult.fail("juryDept 不能为空");
        }
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        juryDeptService.add(juryDept);
        return HttpResult.ok();
    }

    /**
     * 修改评委会主管部门
     */
    @PutMapping()
    public HttpResult update(@RequestBody JuryDeptDTO juryDept) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        juryDeptService.update(juryDept);
        return HttpResult.ok();
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
        strings.add(juryDeptDTO.getCity());
        strings.add(juryDeptDTO.getCounty());
        juryDeptDTO.setCitys(strings);
        return HttpResult.ok(juryDeptDTO);
    }

    /**
     * 根据ID删除评委会主管部门
     *
     * @param id 部门详情id
     * @return 删除数量
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        // 普通用户不能访问该接口
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            return HttpResult.fail("无访问权限");
        }
        if (id != null) {
            int del = juryDeptService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
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

    /**
     * 根据登录用户id查询部门类型
     * @return 1 主管部门 2评委会
     */
    @GetMapping("/getDeptTypeByUserId")
    public HttpResult getDeptTypeByUserId(){
        User user=tokenService.getLoginUserToUser();
        Integer deptType;
        if(user.getUserType()==3){
            deptType = iJuryDeptService.selectByUserId(user.getUserId()).getDeptType();
            return HttpResult.ok(deptType);
        } else {
            throw new NoticeException("当前登录用户不是评委会或者主管部门");
        }
    }

    /**
     * 审核评委会接口
     * @param juryDeptDTO deptId 部门id checkState true：通过 false: 拒绝
     * @return 执行成功与否
     */
    @PostMapping("/juryCheck")
    public HttpResult juryCheck(@RequestBody JuryDeptDTO juryDeptDTO){
        boolean result = iJuryDeptService.checkJury(juryDeptDTO.getDeptId(), juryDeptDTO.getCheckState());
        return HttpResult.ok(result);
    }
}
