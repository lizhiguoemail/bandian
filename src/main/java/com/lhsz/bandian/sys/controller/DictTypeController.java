package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.result.DictTypeDTO;
import com.lhsz.bandian.sys.entity.DictType;
import com.lhsz.bandian.sys.service.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/sys/dict-type")
public class DictTypeController extends BaseController {
    @Autowired
    private IDictTypeService iDictTypeService;

    /**
     * 查询字典类型
     */
    @GetMapping()
    public HttpResult listDictType(DictType dictType) {
        startPage();
        List<DictTypeDTO> list = iDictTypeService.list(dictType);
        return HttpResult.ok(getDataTable(list));
    }

    /**
     * 保存字典类型
     */
    @PostMapping()
    public HttpResult add(@RequestBody DictTypeDTO dictTypeDTO) {
        iDictTypeService.save(dictTypeDTO);
        return HttpResult.ok();
    }

    /**
     * 修改字典类型
     */
    @PutMapping()
    public HttpResult update(@RequestBody DictTypeDTO dictTypeDTO) {
        iDictTypeService.update(dictTypeDTO);
        return HttpResult.ok();
    }

    /**
     * 字典类型详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        DictTypeDTO dictTypeDTO = iDictTypeService.selectById(id);
        return HttpResult.ok(dictTypeDTO);
    }

    /**
     * 根据ID删除字典类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iDictTypeService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}
