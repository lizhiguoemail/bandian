package com.lhsz.bandian.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/24 11:32
 */
@Data
public class BaseTree implements Serializable {
    private String id;
    private String parentId;
    private List<?> children;
}
