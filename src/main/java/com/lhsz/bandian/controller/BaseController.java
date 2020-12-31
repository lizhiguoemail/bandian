package com.lhsz.bandian.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhsz.bandian.DTO.PageDataInfo;
import com.lhsz.bandian.DTO.page.PageDomain;
import com.lhsz.bandian.DTO.page.TableSupport;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.utils.SqlUtil;
import com.lhsz.bandian.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class BaseController {
    private Page page=null;
    protected Logger logb=log;
    @Autowired
    protected TokenService tokenService;
    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
             page =  PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
    protected PageDataInfo getDataTable(List<?> list){
        PageDataInfo pageDataInfo=new PageDataInfo(list);
//        PageInfo<?> pageInfo=new PageInfo<>(list);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        pageDataInfo.setOrder(pageDomain.getOrderBy());
        pageDataInfo.setPage(page.getPageNum());
        pageDataInfo.setPageCount(page.getPages());
        pageDataInfo.setPageSize(page.getPageSize());
        pageDataInfo.setTotalCount(page.getTotal());
        return pageDataInfo;
    }

}
