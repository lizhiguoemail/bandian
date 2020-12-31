package com.lhsz.bandian.portal.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.cms.DTO.query.CmsArticleSearchDTO;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTO;
import com.lhsz.bandian.cms.DTO.request.CmsArticleDTODetail;
import com.lhsz.bandian.cms.entity.CmsArticle;
import com.lhsz.bandian.cms.entity.CmsArticleChannel;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.lhsz.bandian.cms.service.ICmsArticleChannelService;
import com.lhsz.bandian.cms.service.ICmsArticleService;
import com.lhsz.bandian.cms.service.ICmsChannelService;
import com.lhsz.bandian.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章内容 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@RestController("portalCms")
@RequestMapping("/portal/cms/article")
public class CmsArticleController extends BaseController {
    @Autowired
    private ICmsArticleService iCmsArticleService;
    @Autowired
    private ICmsChannelService iCmsChannelService;
    @Autowired
    private ICmsArticleChannelService iCmsArticleChannelService;


   /* *//**
     * 文章详情
     * @param id 文章id
     * @return
     *//*
    @GetMapping("/{id}")
    public void selectById(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        CmsArticleDTO cmsArticleDTO = iCmsArticleService.getCmsArticle(id);
        iCmsArticleService.updateArticleClicks(id);
        HttpUtils.write(response,cmsArticleDTO);
//        return HttpResult.ok(cmsArticleDTO);
    }*/
    /**
     * 文章详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        CmsArticleDTODetail cmsArticleDTO = iCmsArticleService.getCmsArticle(id);
        iCmsArticleService.updateArticleClicks(id);
        return HttpResult.ok(cmsArticleDTO);
    }
    /**
     * 根据文章类型查询
     * @param code
     * @return
     */
    @GetMapping("/list")
    public HttpResult selectByDictType(String code) {
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
     * 根据文章类型查询
     * @param code
     * @return
     */
    @GetMapping("/page")
    public HttpResult page(String code) {
        startPage();
        CmsChannel cmsChannel = iCmsChannelService.getCmsChannelCode(code);
        List<CmsArticleChannel> cmsArticleChannel = iCmsArticleChannelService.getCmsArticleChannelId(cmsChannel.getChannelId());
        List<CmsArticle> list1 = new ArrayList();
        for(CmsArticleChannel list : cmsArticleChannel) {
            CmsArticle cmsArticleDTO = iCmsArticleService.selectByArticleId(list.getArticleId());
            list1.add(cmsArticleDTO);
        }
        return HttpResult.ok(getDataTable(list1));
    }

    /**
     * 查询文章全部内容
     */
    @GetMapping()
    public HttpResult listDictType(CmsArticleSearchDTO cmsArticleSearchDTO) {
        startPage();
        CmsArticle cmsArticle = new CmsArticle();
        BeanUtils.copyProperties(cmsArticleSearchDTO,cmsArticle);
        List<CmsArticleDTO> cmsArticleDTOS = iCmsArticleService.selectList(cmsArticle);
/*
        if(cmsArticleDTOS == null){
            System.out.println("cmsArticleDTOS == null");
        }
        PageDataInfo dataTable = getDataTable(cmsArticleDTOS);
*/
        return HttpResult.ok(cmsArticleDTOS);
    }


}
