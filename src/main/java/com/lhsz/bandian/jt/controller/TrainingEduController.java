package com.lhsz.bandian.jt.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.controller.BaseController;
import com.lhsz.bandian.jt.DTO.request.TrainingEduAddDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduDetailDTO;
import com.lhsz.bandian.jt.DTO.request.TrainingEduUpdateDTO;
import com.lhsz.bandian.jt.entity.TrainingEdu;
import com.lhsz.bandian.jt.service.ITrainingEduService;
import com.lhsz.bandian.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 继续教育 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/jt/training-edu")
public class TrainingEduController extends BaseController {
    @Autowired
    private ITrainingEduService iTrainingEduService;
    /**
     * 查询继续教育
     */
    @GetMapping()
    public HttpResult list(TrainingEdu trainingEdu) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            trainingEdu.setUserId(user.getUserId());
        }
        startPage();
        trainingEdu.setUserId(trainingEdu.getUserId());
        return HttpResult.ok(getDataTable(iTrainingEduService.list(trainingEdu)) );
    }

    /**
     * 保存继续教育
     */
    @PostMapping()
    public HttpResult add(@RequestBody TrainingEduAddDTO trainingEduAddDTO) {
        try {
            // 普通用户不能访问该接口
            User user=tokenService.getLoginUserToUser();
            if (user.getUserType() == 2) {
                trainingEduAddDTO.getData().setUserId(user.getUserId());
            } else {
                if(trainingEduAddDTO.getData().getUserId() == null) {
                    return HttpResult.fail("用户信息不能为空");
                }
            }
            trainingEduAddDTO.getData().setChkStatus("00");
            iTrainingEduService.add(trainingEduAddDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("添加继续教育异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 修改继续教育
     */
    @PutMapping()
    public HttpResult update(@RequestBody TrainingEduUpdateDTO trainingEduUpdateDTO) {
        try {
            User user=tokenService.getLoginUserToUser();
            // 阻止普通用户为他人添加信息
            if(user.getUserType() == 2){
                if("03".equals(trainingEduUpdateDTO.getData().getChkStatus()) || "04".equals(trainingEduUpdateDTO.getData().getChkStatus())){
                    return HttpResult.fail("当前审核状态不允许添加");
                }
                if(!(user.getUserId().equals(trainingEduUpdateDTO.getData().getUserId()))){
                    return HttpResult.fail("不允许为他人修改信息");
                }
            }
            iTrainingEduService.update(trainingEduUpdateDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            log.debug("修改继续教育异常",e);
            return HttpResult.error("系统内部异常");
        }
    }

    /**
     * 继续教育详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpResult selectById(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            TrainingEduDetailDTO trainingEduDetailDTO = iTrainingEduService.detailById(id);
            if (!(trainingEduDetailDTO.getData().getUserId().equals(user.getUserId()))){
                return HttpResult.fail("不允许普通用户查询他人信息");
            }
            return HttpResult.ok(trainingEduDetailDTO);
        }
        TrainingEduDetailDTO trainingEduDetailDTO = iTrainingEduService.detailById(id);
        return HttpResult.ok(trainingEduDetailDTO);


    }

    /**
     * 根据ID删除继续教育
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") String id) {
        User user = tokenService.getLoginUserToUser();
        if (user.getUserType() == 2){
            TrainingEduDetailDTO trainingEduDetailDTO = iTrainingEduService.detailById(id);
            if ("03".equals(trainingEduDetailDTO.getData().getChkStatus()) || "04".equals(trainingEduDetailDTO.getData().getChkStatus())){
                return HttpResult.fail("当前审核状态不允许删除");
            }
            if (!(trainingEduDetailDTO.getData().getUserId().equals(user.getUserId()))) {
                return HttpResult.fail("不允许为他人删除信息");
            }
        }
        if (id != null) {
            int del = iTrainingEduService.del(id);
            return HttpResult.ok(del);
        }
        return HttpResult.error();
    }
}
