package com.lhsz.bandian.jt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareCategoryDTO;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.service.IDeclareCategoryService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 职称系列类别 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/declare-category")
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

    /**
     * 添加职称系列类别 
     * @param jtDeclareCategoryDTO
     * @return
     */
    @PostMapping()
    public HttpResult addDeclareCategory (@RequestBody JtDeclareCategoryDTO jtDeclareCategoryDTO) {
        jtDeclareCategoryDTO.setIsDeleted(0);
        iDeclareCategoryService.addDeclareCategory(jtDeclareCategoryDTO);
        return HttpResult.ok();
    }

    /**
     *更新职称系列类别 
     * @param jtDeclareCategoryDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateDeclareCategory (@RequestBody JtDeclareCategoryDTO jtDeclareCategoryDTO){
        iDeclareCategoryService.updateDeclareCategory(jtDeclareCategoryDTO);
        return HttpResult.ok();
    }

    /**
     * 删除职称系列类别 
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delDeclareCategory (@PathVariable("id") String Id){
        DeclareCategory dec = iDeclareCategoryService.getById(Id);
        if(dec.getParentId()==null){
            logb.error("用户 {} 删除职称根节点"+tokenService.getUsername());
            return HttpResult.fail("根节点不能删除");
        }
        int delDeclareCategory = iDeclareCategoryService.delDeclareCategory(Id);
        if (delDeclareCategory != 1){
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
    @ApiImplicitParam(name = "ids", value = "ID集合，多个ID逗号隔开", dataType = "String")
    @PostMapping("/delete")
    public HttpResult deleteBatch (@RequestBody() String ids) {
        ids = ids.replace("\"", "");
        if(iDeclareCategoryService.removeByIds(Arrays.asList(ids.split(",")))){
//            String[] id = ids.split(",");
//            for(String articleId : id){
//                QueryWrapper qw = new QueryWrapper();
//                qw.eq("article_id", articleId);
//                iCmsArticleChannelService.remove(qw);
//            }
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

    @GetMapping("/tree")
    public HttpResult tree(){
        TreeDTO treeDTO=iDeclareCategoryService.getTreeDTO();
        return HttpResult.ok(treeDTO);
    }
}
