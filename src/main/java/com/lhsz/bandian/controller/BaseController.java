package com.lhsz.bandian.controller;

import com.github.pagehelper.PageHelper;
import com.lhsz.bandian.DTO.page.PageDomain;
import com.lhsz.bandian.DTO.page.TableSupport;
import com.lhsz.bandian.utils.SqlUtil;
import com.lhsz.bandian.utils.StringUtils;

public class BaseController {
    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
}
