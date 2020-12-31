package com.lhsz.bandian.portal.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareTitle;
import com.lhsz.bandian.jt.service.IDeclareTitleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController("declareTitle")
@RequestMapping("/portal/jt/declare-title")
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
