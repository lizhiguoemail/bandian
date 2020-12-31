package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.sys.DTO.result.DictDataDTO;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.sys.entity.DictData;
import com.lhsz.bandian.sys.service.IDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@RestController
    @RequestMapping("/sys/dict-data")
public class DictDataController extends BaseController {
    @Autowired
    private IDictDataService iDictDataService;
    /**
     * 查询字典数据
     */
    @GetMapping()
    public HttpResult listDictType(DictData dictData) {
        startPage();
        List<DictDataDTO> list = iDictDataService.list(dictData);
        return HttpResult.ok(getDataTable(list) );
    }
    /**
     * 根据字典类型查询
     *
     * @param dictType
     * @return
     */
    @GetMapping("/list/{dictType}")
    public HttpResult selectByDictType(@PathVariable("dictType") String dictType) {
        List<SelectDTO> dictDatasDTOS = iDictDataService.selectByDictType(dictType);
        return HttpResult.ok( dictDatasDTOS);
    }

    @GetMapping("/list/{dictType}/{dictValue}")
    public   HttpResult  selectByDictTypeAndValue(@PathVariable("dictType")  String dictType,@PathVariable("dictValue")  String dictValue){
        String s = iDictDataService.selectByDictTypeAndValue2(dictType, dictValue);
        return  HttpResult.ok(s);
    }
    /**
     * 保存字典数据
     */
    @PostMapping()
    public HttpResult add(@RequestBody DictDataDTO dictDataDTO) {
        iDictDataService.save(dictDataDTO);
        return HttpResult.ok();
    }

    /**
     * 修改字典数据
     */
    @PutMapping()
    public HttpResult update(@RequestBody DictDataDTO dictDataDTO) {
        iDictDataService.update(dictDataDTO);
        return HttpResult.ok();
    }

    /**
     * 字典数据详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        DictDataDTO dictDataDTO = iDictDataService.selectById(id);
        return HttpResult.ok(dictDataDTO);


    }

    /**
     * 根据ID删除字典数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iDictDataService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }


}
