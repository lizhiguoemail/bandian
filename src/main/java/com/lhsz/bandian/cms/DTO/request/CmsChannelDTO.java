package com.lhsz.bandian.cms.DTO.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.lhsz.bandian.jt.DTO.response.JtDeclareCategoryDTO;
import com.lhsz.bandian.sys.DTO.result.BaseDTO;
import com.lhsz.bandian.utils.CopyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author LSCHOME
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CmsChannel对象", description="频道类型")
public class CmsChannelDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    public CmsChannelDTO() {
    }
    public CmsChannelDTO(CmsChannel cmsChannel){
        CopyUtils.copyProperties(cmsChannel,this);
        this.setId(cmsChannel.getChannelId());
    }

    @ApiModelProperty(value = "频道标识")
    @TableId(value = "channel_id", type = IdType.ASSIGN_UUID)
    private String channelId;

    @ApiModelProperty(value = "频道编码")
    private String code;

    @ApiModelProperty(value = "频道名称")
    private String name;

    @ApiModelProperty(value = "频道类型")
    private Integer type;

    @ApiModelProperty(value = "完整URL")
    private String fullUrl;

    @ApiModelProperty(value = "父标识")
    private String parentId;

    @ApiModelProperty(value = "拼音简码")
    private String pinYin;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "启用")
    private Boolean enabled;

    private boolean extend;
    private boolean leaf;

    private List<CmsChannelDTO> children;
}
