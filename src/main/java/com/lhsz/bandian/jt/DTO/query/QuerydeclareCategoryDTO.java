package com.lhsz.bandian.jt.DTO.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/24 15:30
 */
@Data
public class QuerydeclareCategoryDTO implements Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private boolean enabled=true;
}
