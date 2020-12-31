package com.lhsz.bandian.jt.controller;


import com.itextpdf.text.DocumentException;
import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCheckCountDTO;
import com.lhsz.bandian.jt.DTO.request.TitleApplyCheckDTO;
import com.lhsz.bandian.jt.DTO.response.TitleApplyDTO;
import com.lhsz.bandian.jt.entity.JuryDeptUser;
import com.lhsz.bandian.jt.entity.TitleApply;
import com.lhsz.bandian.jt.service.IJuryDeptUserService;
import com.lhsz.bandian.jt.service.ITitleApplyService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.utils.DateUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 申报职称 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/title-apply")
public class TitleApplyController extends BaseController {
    private final ITitleApplyService iTitleApplyService;
    private final TokenService tokenService;
    private final IJuryDeptUserService iJuryDeptUserService;

    @Autowired
    public TitleApplyController(ITitleApplyService iTitleApplyService, TokenService tokenService, IJuryDeptUserService iJuryDeptUserService) {
        this.iTitleApplyService = iTitleApplyService;
        this.tokenService = tokenService;
        this.iJuryDeptUserService = iJuryDeptUserService;
    }

    /**
     * 查询申报职称
     */
    @GetMapping()
    public HttpResult list(TitleApply titleApply) {
        User user=tokenService.getLoginUserToUser();
        try {
            JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(user.getUserId());
            titleApply.setChkOfficeId(juryDeptUser.getDeptId());
        } catch (Exception e) {
            titleApply.setChkOfficeId(null);
        }
        // 会员只能查询本userId的记录
        if(user.getUserType() == 2) {
            titleApply.setUserId(user.getUserId());
        }
        startPage();
        return HttpResult.ok(getDataTable(iTitleApplyService.list(titleApply)) );
    }

    /**
     * 保存申报职称
     */
    @PostMapping()
    public HttpResult add(@RequestBody TitleApplyDTO titleApplyDTO) {
        try {
            User user = tokenService.getLoginUserToUser();
            if (user.getUserType() == 2){
                // 验证当前是否有待审核的申请
                TitleApply query = new TitleApply();
                query.setUserId(user.getUserId());
                List<TitleApplyDTO> list = iTitleApplyService.list(query);
                if (list.size()>0){
                    return HttpResult.fail("你当前存在待审核的评审计划");
                }
            }
            iTitleApplyService.add(titleApplyDTO);
            return HttpResult.ok();
        } catch (NoticeException noticeException){
            return HttpResult.error(noticeException.getMessage());
        } catch (Exception e) {
            log.error("保存申报职称异常", e);
            return HttpResult.error(e.getMessage());
        }
    }

    /**
     * 提交申报职称审核结果
     */
    @PostMapping("/check")
    public HttpResult addCheck(@RequestBody TitleApplyCheckDTO titleApplyCheckDTO) {
        try {
            iTitleApplyService.addCheck(titleApplyCheckDTO);
            return HttpResult.ok();
        }catch (NoticeException noticeException){
            return HttpResult.fail(noticeException.getMessage());
        }
        catch (Exception e) {
            log.error("提交申报职称审核结果失败：", e);
            return HttpResult.fail("提交申报职称审核结果失败");
        }

    }

    /**
     * 重新提交申报职称审核结果
     */
    @PutMapping("/check")
    public HttpResult updateResetCheck(@RequestBody TitleApplyCheckDTO titleApplyCheckDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            titleApplyCheckDTO.setUserId(user.getUserId());
            titleApplyCheckDTO.setChkTime(DateUtils.getNowDate());
            titleApplyCheckDTO.setChkOfficeId("");
            titleApplyCheckDTO.setChkOfficeName("");
            titleApplyCheckDTO.setChkUserId("");
            titleApplyCheckDTO.setChkUserName("");
            iTitleApplyService.addResetCheck(titleApplyCheckDTO);
            return HttpResult.ok();
        }catch (NoticeException noticeException){
            return HttpResult.fail("重新提交失败: "+ noticeException.getMessage());
        }
        catch (Exception e) {
            log.error("重新提交职称审核结果失败：", e);
            return HttpResult.fail("重新提交失败");
        }

    }

    /**
     * 修改申报职称
     */
    @PutMapping()
    public HttpResult update(@RequestBody TitleApplyDTO titleApplyDTO) {
        iTitleApplyService.update(titleApplyDTO);
        return HttpResult.ok();
    }

    /**
     * 申报职称详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        TitleApplyDTO titleApplyDTO = iTitleApplyService.selectById(id);
        return HttpResult.ok(titleApplyDTO);
    }

    /**
     * 得到当前登录用户的申报职称详情
     *
     * @return 申报职称详情
     */
    @GetMapping("/user")
    public HttpResult selectByUserId() {
        User user=tokenService.getLoginUserToUser();
        TitleApplyDTO titleApplyDTO = iTitleApplyService.selectByUserId(user.getUserId());
        return HttpResult.ok(titleApplyDTO);
    }

    /**
     * 根据ID删除申报职称
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        if (id != null) {
            int del = iTitleApplyService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }

    /**
     * 获取职称评审表
     *
     * @param userId 用户id
     * */
    @GetMapping("/pdf")
    @ResponseBody
    public HttpResult pdf(String userId) {
        try {
            User user = tokenService.getLoginUserToUser();
            String newpath;
            if (user.getUserType() == 2) {
                newpath = iTitleApplyService.getPDF(user.getUserId());
            }else {
                newpath = iTitleApplyService.getPDF(userId);
            }
            // 发送生成的PDF文件路径
            ArrayList<String> result = new ArrayList<>();
            result.add(newpath);
            return HttpResult.ok(result);
        } catch (IOException e) {
            log.error("生成 PDF I/O 流异常", e);
            return HttpResult.fail("生成 PDF I/O 流异常");
        } catch (DocumentException e) {
            log.error("生成 PDF 文档异常", e);
            return HttpResult.fail("生成 PDF 文档异常");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除")
    @ApiImplicitParam(name = "ids", value = "ID集合，多个ID逗号隔开", dataType = "String")
    @PostMapping("/delete")
    public HttpResult deleteBatch (@RequestBody() String ids) {
        ids = ids.replace("\"", "");
        if(iTitleApplyService.removeByIds(Arrays.asList(ids.split(",")))){
            return HttpResult.ok();
        }
        else {
            return HttpResult.fail();
        }
    }

    /**
     * 查询 userId 各业绩库下记录条数
     * */
    @ApiOperation(value = "查询userId各业绩库下记录条数")
    @GetMapping("/count")
    public HttpResult getCount(String userId){
        try {
            User user=tokenService.getLoginUserToUser();
            if(user.getUserType() == 2){
                userId = user.getUserId();
            }
            TitleApplyCheckCountDTO checkCount = iTitleApplyService.getCheckCount(userId);
            return HttpResult.ok(checkCount);
        } catch (Exception e) {
            log.error("查询userId各业绩库下记录条数异常: ", e);
            return HttpResult.fail("查询userId各业绩库下记录条数异常: "+e.getMessage());
        }
    }

    /**
     * 获取证书
     *
     * @param userId 用户id
     * */
    @GetMapping("/getCertificate")
    @ResponseBody
    public HttpResult getCertificate(String userId) {
        try {
            User user = tokenService.getLoginUserToUser();
            String newpath;
            if (user.getUserType() == 2) {
                newpath = iTitleApplyService.getCertificate(user.getUserId());
            }else {
                newpath = iTitleApplyService.getCertificate(userId);
            }
            // 发送生成的PDF文件路径
            ArrayList<String> result = new ArrayList<>();
            result.add(newpath);
            return HttpResult.ok(result);
        } catch (IOException e) {
            log.error("生成 PDF I/O 流异常", e);
            return HttpResult.fail("生成 PDF I/O 流异常");
        } catch (DocumentException e) {
            log.error("生成 PDF 文档异常", e);
            return HttpResult.fail("生成 PDF 文档异常");
        }
    }

}
