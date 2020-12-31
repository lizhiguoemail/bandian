package com.lhsz.bandian.cms.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.cms.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.cms.DTO.request.CmsChannelDTO;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.lhsz.bandian.cms.service.ICmsChannelService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章频道 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/cms/channel")
public class CmsChannelController extends BaseController {

    private final ICmsChannelService iCmsChannelService;

    public CmsChannelController(ICmsChannelService iCmsChannelService) {
        this.iCmsChannelService = iCmsChannelService;
    }

    /**
     * 查询频道类别
     * @param querydeclareCategoryDTO
     * @return
     */
    @GetMapping()
    public HttpResult ListDeclareCategory (QuerydeclareCategoryDTO querydeclareCategoryDTO) {
        List<CmsChannelDTO> jtDeclareCategoryDTOS = iCmsChannelService.selectList(querydeclareCategoryDTO);
        return HttpResult.ok(jtDeclareCategoryDTOS);
    }
    /**
     * 查询系列类别
     */
    @ApiOperation(value = "查询频道父类，parentId为空返回父类")
    @ApiImplicitParam(name = "parentId", value = "父类ID", dataType = "String")
    @GetMapping("/all/{parentId}")
    public HttpResult listAllByParentId(@PathVariable("parentId") String parentId) {
        List<SelectDTO> result = iCmsChannelService.listAllByParentId(parentId);
        return HttpResult.ok(result);
    }

    /**
     * 根据id进行查询
     * @param Id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult getDeclareCategory (@PathVariable("id") String Id){
        CmsChannelDTO declareCategory = iCmsChannelService.getCmsChannel(Id);
        if (declareCategory != null){
            return HttpResult.ok(declareCategory);
        }
        return HttpResult.fail();
    }

    /**
     * 添加职称系列类别
     * @param cmsChannelDTO
     * @return
     */
    @PostMapping()
    public HttpResult addDeclareCategory (@RequestBody CmsChannelDTO cmsChannelDTO) {
        CmsChannel cmsChannel = iCmsChannelService.getCmsChannelCode(cmsChannelDTO.getCode());
        if(cmsChannel!=null){
            return HttpResult.fail("频道已存在！");
        }
        cmsChannelDTO.setIsDeleted(0);
        iCmsChannelService.addCmsChannel(cmsChannelDTO);
        return HttpResult.ok();
    }

    /**
     *更新职称系列类别
     * @param cmsChannelDTO
     * @return
     */
    @PutMapping()
    public HttpResult updateDeclareCategory (@RequestBody CmsChannelDTO cmsChannelDTO){
        iCmsChannelService.updateCmsChannel(cmsChannelDTO);
        return HttpResult.ok();
    }

    /**
     * 删除职称系列类别
     * @param Id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delDeclareCategory (@PathVariable("id") String Id){
        CmsChannel dec = iCmsChannelService.getById(Id);
        if(dec.getParentId()==null){
            logb.error("用户 {} 删除职称根节点"+tokenService.getUsername());
            return HttpResult.fail("根节点不能删除");
        }
        int delDeclareCategory = iCmsChannelService.delCmsChannelDTO(Id);
        if (delDeclareCategory != 1){
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
    @GetMapping("/tree")
    public HttpResult tree(){
        TreeDTO treeDTO=iCmsChannelService.getTreeDTO();
        return HttpResult.ok(treeDTO);
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
        if(iCmsChannelService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

}
