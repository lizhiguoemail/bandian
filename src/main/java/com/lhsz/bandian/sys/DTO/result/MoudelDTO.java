package com.lhsz.bandian.sys.DTO.result;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/20 11:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="权限菜单对象", description="系统参数")
public class MoudelDTO extends BaseDTO {

    private String applicationId;
    private String applicationName;
    private boolean checked;
    private List<MoudelDTO> children;
//    private LocalDateTime creationTime;
//    private String creatorId;
    private String disableCheckbox;
    private boolean enabled;
    private boolean expanded;
    private String icon;
    private boolean leaf;
    private Integer level;
    private String method;
    private String name;
    private List<MoudelDTO> operators;
    private String parentId;
    private String parentName;
    private String path;
//    private String remark;
    private boolean selectable;
    private String selected;
    private Integer sortId;
    private String text;
    private Integer type;
    private String url;
    private String title;
//    private Integer version;
}
