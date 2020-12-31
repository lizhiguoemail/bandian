package com.lhsz.bandian.portal.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JtDeclareSpecialtyDTO;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
import com.lhsz.bandian.jt.service.IDeclareSpecialtyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 申报专业 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController("declareSpecialty")
@RequestMapping("/portal/jt/declare-specialty")
public class DeclareSpecialtyController extends BaseController {

    @Autowired
    private IDeclareSpecialtyService iDeclareSpecialtyService;

    /**
     * 查询申报专业
     * @param declareSpecialty
     * @return
     */
    @ApiOperation(value = "查询职称查询专业数据，declareSpecialty.categoryId不能为空")
    @ApiImplicitParam(name = "categoryId", value = "类别ID", dataType = "String")
    @GetMapping()
    public HttpResult ListDeclareSpecialty (DeclareSpecialty declareSpecialty) {
        startPage();
        List<JtDeclareSpecialtyDTO> jtDeclareSpecialtyDTOS = iDeclareSpecialtyService.listDeclareSpecialty(declareSpecialty);
        return HttpResult.ok(getDataTable(jtDeclareSpecialtyDTOS));
    }

    /**
     * 根据id进行查询
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getDeclareSpecialty (@PathVariable("id") String Id){
        JtDeclareSpecialtyDTO declareSpecialty = iDeclareSpecialtyService.getDeclareSpecialty(Id);
        if (declareSpecialty != null){
            return HttpResult.ok(declareSpecialty);
        }
        return HttpResult.fail();
    }

    /**
     * 添加申报专业
     * @param jtDeclareSpecialtyDTO
     * @return
     */
    @PostMapping()
    public HttpResult addDeclareSpecialty (@RequestBody JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO) {
        if(jtDeclareSpecialtyDTO.getCategoryId()==null||"".equals(jtDeclareSpecialtyDTO.getCategoryId().trim())){
            return HttpResult.fail("categoryId不能为空");
        }
        iDeclareSpecialtyService.addDeclareSpecialty(jtDeclareSpecialtyDTO);
        return HttpResult.ok();
    }

    /**
     *更新申报专业
     * @param jtDeclareSpecialtyDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateDeclareSpecialty (@RequestBody JtDeclareSpecialtyDTO jtDeclareSpecialtyDTO){
        DeclareCategory declareCategory=new DeclareCategory();
        iDeclareSpecialtyService.updateDeclareSpecialty(jtDeclareSpecialtyDTO);
        return HttpResult.ok();
    }

    /**
     * 删除申报专业
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delDeclareSpecialty (@PathVariable("id") String Id){
        int delDeclareSpecialty = iDeclareSpecialtyService.delDeclareSpecialty(Id);
        if (delDeclareSpecialty != 1){
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
        if(iDeclareSpecialtyService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

    /**
     * 查询系列类别
     */
    @ApiOperation(value = "根据类别ID查询该类别下的专业")
    @ApiImplicitParam(name = "categoryId", value = "类别ID", dataType = "String")
    @GetMapping("/all/{categoryId}")
    public HttpResult listAllByCategoryId(@PathVariable("categoryId") String categoryId) {
        List<SelectDTO> result = iDeclareSpecialtyService.listAllByCategoryId(categoryId);
        return HttpResult.ok(result);
    }
}
