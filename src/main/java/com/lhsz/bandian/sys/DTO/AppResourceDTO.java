package com.lhsz.bandian.sys.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/13 9:18
 */
public class AppResourceDTO implements Serializable {
    private static final long serialVersionUID=1L;
    private String id;
    private String parentId;
    private String text;
    private String icon;
    private String link;
    private String externalLink;
    private String target;
    private String i18n;
    private boolean group;
    private boolean hideInBreadcrumb;
    private List<AppResourceDTO> children;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public List<AppResourceDTO> getChildren() {
        return children;
    }

    public void setChildren(List<AppResourceDTO> children) {
        this.children = children;
    }
}
