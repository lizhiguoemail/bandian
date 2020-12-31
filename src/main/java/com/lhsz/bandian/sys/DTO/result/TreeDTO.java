package com.lhsz.bandian.sys.DTO.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/24 9:44
 */
@Data
public class TreeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String expandedKeys;
    private List<?> nodes;
}
