package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.result.OperatorLogDTO;
import com.lhsz.bandian.sys.entity.OperatorLog;
import com.lhsz.bandian.sys.service.IOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/sys/operator-log")
public class OperatorLogController extends BaseController {
    @Autowired
    private IOperatorLogService iOperatorLogService;
    /**
     * 查询登陆日志
     */
    @GetMapping()
    public HttpResult listDictType(OperatorLog log) {
        startPage();
        List<OperatorLogDTO> list = iOperatorLogService.list(log);
        return HttpResult.ok(getDataTable(list));
    }
    /**
     * 登陆日志详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        OperatorLogDTO operatorLogDTO = iOperatorLogService.selectById(id);
        return HttpResult.ok(operatorLogDTO);


    }

    /**
     * 根据ID删除登陆日志
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iOperatorLogService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}
