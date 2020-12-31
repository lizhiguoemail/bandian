package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.jt.DTO.response.TitleCertDTO;
import com.lhsz.bandian.jt.entity.TitleCert;
import com.lhsz.bandian.jt.service.ITitleCertService;
import com.lhsz.bandian.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;

/**
 * <p>
 * 职称证书 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/jt/title-cert")
public class TitleCertController extends BaseController {
    @Autowired
    private ITitleCertService iTitleCertService;
    /**
     * 查询职称证书
     */
    @GetMapping()
    public HttpResult list(TitleCert titleCert) {
        // 会员只能查询本userId的记录
        User user=tokenService.getLoginUserToUser();
        if(user.getUserType() == 2) {
            titleCert.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iTitleCertService.list(titleCert)) );
    }

    /**
     * 保存职称证书
     */
    @PostMapping()
    public HttpResult add(@RequestBody TitleCertDTO titleCertDTO) {

        iTitleCertService.add(titleCertDTO);
        return HttpResult.ok();
    }

    /**
     * 修改职称证书
     */
    @PutMapping()
    public HttpResult update(@RequestBody TitleCertDTO titleCertDTO) {
        iTitleCertService.update(titleCertDTO);
        return HttpResult.ok();
    }

    /**
     * 职称证书详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        TitleCertDTO titleCertDTO = iTitleCertService.selectById(id);
        return HttpResult.ok(titleCertDTO);


    }

    /**
     * 根据ID删除职称证书
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iTitleCertService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}
