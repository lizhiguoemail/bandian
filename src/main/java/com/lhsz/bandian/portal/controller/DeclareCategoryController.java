package com.lhsz.bandian.portal.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareCategoryDTO;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.service.IDeclareCategoryService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职称系列类别 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController("declareCategoryCms")
@RequestMapping("/portal/jt/declare-category")
public class DeclareCategoryController extends BaseController {
    
    private final IDeclareCategoryService iDeclareCategoryService;

    public DeclareCategoryController(IDeclareCategoryService iDeclareCategoryService) {
        this.iDeclareCategoryService = iDeclareCategoryService;
    }

    /**
     * 查询职称系列类别 
     * @param querydeclareCategoryDTO
     * @return
     */
    @GetMapping()
    public HttpResult ListDeclareCategory (QuerydeclareCategoryDTO querydeclareCategoryDTO) {
        List<JtDeclareCategoryDTO> jtDeclareCategoryDTOS = iDeclareCategoryService.listDeclareCategory(querydeclareCategoryDTO);
        return HttpResult.ok(jtDeclareCategoryDTOS);
    }
    /**
     * 查询系列类别
     */
    @ApiOperation(value = "查询职称系列类别，parentId为空返回父类")
    @ApiImplicitParam(name = "parentId", value = "父类ID", dataType = "String")
    @GetMapping("/all/{parentId}")
    public HttpResult listAllByParentId(@PathVariable("parentId") String parentId) {
        List<SelectDTO> result = iDeclareCategoryService.listAllByParentId(parentId);
        return HttpResult.ok(result);
    }
    /**
     * 查询系列类别
     */
    @ApiOperation(value = "查询职称系列类别，parentId为空返回父类")
    @ApiImplicitParam(name = "parentId", value = "父类ID", dataType = "String")
    @GetMapping("/all")
    public HttpResult listAll() {
        List<SelectDTO> result = iDeclareCategoryService.listAllByParentId(null);
        return HttpResult.ok(result);
    }

    /**
     * 根据id进行查询
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getDeclareCategory (@PathVariable("id") String Id){
        JtDeclareCategoryDTO declareCategory = iDeclareCategoryService.getDeclareCategory(Id);
        if (declareCategory != null){
            return HttpResult.ok(declareCategory);
        }
        return HttpResult.fail();
    }


    @GetMapping("/tree")
    public HttpResult tree(){
        TreeDTO treeDTO=iDeclareCategoryService.getTreeDTO();
        return HttpResult.ok(treeDTO);
    }

    @GetMapping("/treeList")
    public HttpResult treeList(){
        List<JtDeclareCategoryDTO> tree = iDeclareCategoryService.getTree();
        return HttpResult.ok(tree);
    }
}
