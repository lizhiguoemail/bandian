package com.lhsz.bandian.jt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.response.JtAwardsTypeDTO;
import com.lhsz.bandian.jt.entity.AwardsType;
import com.lhsz.bandian.jt.service.IAwardsTypeService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 资历级别 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/AwardsType-type")
public class AwardsTypeController extends BaseController {

    private final IAwardsTypeService iAwardsTypeTypeService;

    public AwardsTypeController(IAwardsTypeService iAwardsTypeTypeService) {
        this.iAwardsTypeTypeService = iAwardsTypeTypeService;
    }

    /**
     * 查询系列类别
     */
    @ApiOperation(value = "查询资历级别，parentId为空返回父类")
    @ApiImplicitParam(name = "parentId", value = "父类ID", dataType = "String")
    @GetMapping("/all/{parentId}")
    public HttpResult listAllByParentId(@PathVariable("parentId") String parentId) {
        List<SelectDTO> result = iAwardsTypeTypeService.listAllByParentId(parentId);
        return HttpResult.ok(result);
    }

    /**
     * 查询系列类别
     */
    @ApiOperation(value = "查询全部资历级别")
    @GetMapping("/all")
    public HttpResult listAll() {
        List<SelectDTO> result = iAwardsTypeTypeService.listAllByParentId(null);
        return HttpResult.ok(result);
    }

    /**
     * 查询资历级别
     * @param awardsType
     * @return
     */
    @GetMapping()
    public HttpResult ListAwardsType (AwardsType awardsType ) {
        List<JtAwardsTypeDTO> jtAwardsTypeDTOS = iAwardsTypeTypeService.listAwardsType(awardsType);
        if(jtAwardsTypeDTOS.size() > 0){
            return HttpResult.ok(jtAwardsTypeDTOS);
        }
        return HttpResult.fail();
    }

    /**
     * 根据id进行查询
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getAwardsType (@PathVariable("id") String Id){
        JtAwardsTypeDTO awardsType = iAwardsTypeTypeService.getAwardsType(Id);
        if (awardsType != null){
            return HttpResult.ok(awardsType);
        }
        return HttpResult.fail();
    }

    /**
     * 添加资历级别
     * @param jtAwardsTypeDTO
     * @return
     */
    @PostMapping()
    public HttpResult addAwardsType (@RequestBody JtAwardsTypeDTO jtAwardsTypeDTO) {
        jtAwardsTypeDTO.setIsDeleted(0);
        iAwardsTypeTypeService.addAwardsType(jtAwardsTypeDTO);
        return HttpResult.ok();
    }

    /**
     *更新资历级别
     * @param jtAwardsTypeDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateAwardsType (@RequestBody JtAwardsTypeDTO jtAwardsTypeDTO){
        iAwardsTypeTypeService.updateAwardsType(jtAwardsTypeDTO);
        return HttpResult.ok();
    }

    /**
     * 删除资历级别
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delAwardsType (@PathVariable("id") String Id){
        int delAwardsType = iAwardsTypeTypeService.delAwardsType(Id);
        if (delAwardsType != 1){
            return HttpResult.ok();
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
        if(iAwardsTypeTypeService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

    @GetMapping("/tree")
    public HttpResult tree(){
        TreeDTO treeDTO=iAwardsTypeTypeService.getTreeDTO();
        return HttpResult.ok(treeDTO);
    }
}
