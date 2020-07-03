package com.lhsz.bandian.pojo.page;

import lombok.Data;

/**
 * 分页结果的封装(for Layui Table)
 *
 * @author lizhiguo
 * @Date 2020-7-2 10:02:24
 */
@Data
public class Result<T> {
    private Integer code = 0;

    private String msg = "请求成功";

    private T data;
}
