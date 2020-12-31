package com.lhsz.bandian.sys.DTO.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/21 11:31
 */
@Data
public class MoudelParentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean checked;
    private List<MoudelParentDTO> children;
    private boolean expanded;
    private String id;
    @JSONField(name="isLeaf")
    private boolean leaf;
    private String key;
    private Integer level;
    private String parentId;
    private String title;

}
