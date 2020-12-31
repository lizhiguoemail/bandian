package com.lhsz.bandian.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/23 16:31
 */
@Data
public class PageDataInfo implements Serializable {
    private String order="CreationTime desc";
    /**
     * 页码
     */
    private Integer page;
    /**
     * 页数
     */
    private Integer pageCount;
    /**
     * 每页几条
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Long totalCount;
    private Object data;
    public PageDataInfo(Object obj){
        this.data=obj;
    }
}
