package com.lhsz.bandian.sys.DTO.result;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author lizhiguo
 * 2020/7/24 10:10
 */
@Data
public class TreeDataDTO implements Serializable {
    private boolean checked;
    private List<TreeDataDTO> children;
    private String code;
    private boolean expanded;
    private String id;
    @JSONField(name = "isLeaf")
    private boolean leaf;
    private String key;
    private Integer level;
    private String parentId;
    private String title;
}
