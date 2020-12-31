package com.lhsz.bandian.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOAdd;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTODetail;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTOUpdate;
import com.lhsz.bandian.cms.entity.CmsArticle;
import com.lhsz.bandian.cms.entity.CmsArticleChannel;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.lhsz.bandian.cms.service.ICmsArticleChannelService;
import com.lhsz.bandian.cms.service.ICmsArticleService;
import com.lhsz.bandian.cms.service.ICmsChannelService;
import com.lhsz.bandian.controller.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * <p>
 * 文章内容 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/cms/article")
public class CmsArticleController extends BaseController {
    @Autowired
    private ICmsArticleService iCmsArticleService;
    @Autowired
    private ICmsChannelService iCmsChannelService;
    @Autowired
    private ICmsArticleChannelService iCmsArticleChannelService;

    /**
     * 查询文章全部内容
     */
    @GetMapping()
    public HttpResult listDictType(CmsArticle cmsArticle) {
        startPage();
        return HttpResult.ok(getDataTable(iCmsArticleService.selectList(cmsArticle)));
    }

    /**
     * 文章详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        CmsArticleDTODetail cmsArticleDTO = iCmsArticleService.getCmsArticle(id);
        return HttpResult.ok(cmsArticleDTO);
    }

    /**
     * 根据文章类型查询
     * @param code
     * @return
     */
    @GetMapping("/list")
    public HttpResult selectByDictType(String code) {
        startPage();
        CmsChannel cmsChannel = iCmsChannelService.getCmsChannelCode(code);
        List<CmsArticleChannel> cmsArticleChannel = iCmsArticleChannelService.getCmsArticleChannelId(cmsChannel.getChannelId());
        List<CmsArticle> list1 = new ArrayList();
        for(CmsArticleChannel list : cmsArticleChannel) {
            CmsArticle cmsArticleDTO = iCmsArticleService.selectByArticleId(list.getArticleId());
            list1.add(cmsArticleDTO);
        }
        return HttpResult.ok(list1);
    }

    /**
     * 添加文章
     * @param cmsArticleDTO
     * @return
     */
    @PostMapping()
    public HttpResult addDeclareCategory (@RequestBody CmsArticleDTOAdd cmsArticleDTO) {
        cmsArticleDTO.getData().setIsDeleted(0);
        iCmsArticleService.addCmsChannel(cmsArticleDTO);
        return HttpResult.ok();
    }

    /**
     * 修改文章内容
     */
    @PutMapping()
    public HttpResult update(@RequestBody CmsArticleDTOUpdate cmsArticleDTO) {
        iCmsArticleService.updateCmsChannel(cmsArticleDTO);
        return HttpResult.ok();
    }

    /**
     * 根据ID删除文章内容
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iCmsArticleService.delCmsArticleDTO(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
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
        if(iCmsArticleService.removeByIds(Arrays.asList(ids.split(",")))){
            String[] id = ids.split(",");
            for(String articleId : id){
                QueryWrapper qw = new QueryWrapper();
                qw.eq("article_id", articleId);
                iCmsArticleChannelService.remove(qw);
            }
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

}
