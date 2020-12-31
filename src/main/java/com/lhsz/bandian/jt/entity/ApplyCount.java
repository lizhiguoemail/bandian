package com.lhsz.bandian.jt.entity;

import lombok.Data;

/**
 * @author zhusenlin
 * Date on 2020/8/21  17:42
 */
@Data
public class ApplyCount {
    public ApplyCount(String type, String date, Integer value) {
        this.type = type;
        this.date = date;
        this.value = value;
    }

    private String type;
    private String date;
    private Integer value;
}
