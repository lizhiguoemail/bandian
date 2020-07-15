package com.lhsz.bandian.sys.DTO;

import com.lhsz.bandian.sys.entity.Application;

import java.io.Serializable;

/**
 * @author lizhiguo
 * 2020/7/13 9:16
 */
public class AppApplicationDTO implements Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private String description;

    public AppApplicationDTO(Application application){
        this.name=application.getName();
        this.description=application.getRemark();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
