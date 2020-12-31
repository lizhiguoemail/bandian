package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareTitle;
import com.lhsz.bandian.jt.service.IDeclareTitleService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 申报职称 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/declare-title")
public class DeclareTitleController extends BaseController {
    @Autowired
    private IDeclareTitleService iDeclareTitleService;

    /**
     * 查询申报职称
     * @param declareTitle
     * @return
     */
    @ApiOperation(value = "根绝类别查询职称数据，declareSpecialty.categoryId不能为空")
    @ApiImplicitParam(name = "categoryId", value = "类别ID", dataType = "String")
    @GetMapping()
    public HttpResult ListDeclareTitle (DeclareTitle declareTitle) {
        startPage();
        if(declareTitle.getCategoryId()==null){
            return HttpResult.fail();
        }
        List<JtDeclareTitleDTO> jtDeclareTitleDTOS = iDeclareTitleService.listDeclareTitle(declareTitle);
        return HttpResult.ok(getDataTable(jtDeclareTitleDTOS));
    }

    /**
     * 根据id进行查询
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getDeclareTitle (@PathVariable("id") String Id){
        JtDeclareTitleDTO declareTitle = iDeclareTitleService.getDeclareTitle(Id);
        if (declareTitle != null){
            return HttpResult.ok(declareTitle);
        }
        return HttpResult.fail();
    }

    /**
     * 添加申报职称
     * @param jtDeclareTitleDTO
     * @return
     */
    @PostMapping()
    public HttpResult addDeclareTitle (@RequestBody JtDeclareTitleDTO jtDeclareTitleDTO) {
        iDeclareTitleService.addDeclareTitle(jtDeclareTitleDTO);
        return HttpResult.ok();
    }

    /**
     *更新申报职称
     * @param jtDeclareTitleDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateDeclareTitle (@RequestBody JtDeclareTitleDTO jtDeclareTitleDTO){
        iDeclareTitleService.updateDeclareTitle(jtDeclareTitleDTO);
        return HttpResult.ok();
    }

    /**
     * 删除申报职称
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delDeclareTitle (@PathVariable("id") String Id){
        int delDeclareTitle = iDeclareTitleService.delDeclareTitle(Id);
        if (delDeclareTitle != 1){
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @ApiImplicitParam(name = "ids", value = "职称ID集合，多个ID逗号隔开", dataType = "String")
    @PostMapping("/delete")
    public HttpResult deleteBatch (@RequestBody() String ids) {
        ids= ids.replace("\"", "");
        if(iDeclareTitleService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

    /**
     * 查询系列类别
     */
    @ApiOperation(value = "根据类别ID查询该类别下的职称")
    @ApiImplicitParam(name = "categoryId", value = "类别ID", dataType = "String")
    @GetMapping("/all/{categoryId}")
    public HttpResult listAllByCategoryId (@PathVariable("categoryId") String categoryId){
        if(categoryId==null||"".equals(categoryId.trim())){
            return HttpResult.fail("categoryId 不能为空！");
        }
        List<SelectDTO> result = iDeclareTitleService.listAllByCategoryId(categoryId);
        return HttpResult.ok(result);
    }

}
