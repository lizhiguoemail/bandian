package com.lhsz.bandian.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.text.DocumentException;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.jt.DTO.request.*;
import com.lhsz.bandian.jt.DTO.response.*;
import com.lhsz.bandian.jt.entity.*;
import com.lhsz.bandian.jt.mapper.TitleApplyMapper;
import com.lhsz.bandian.jt.service.*;
import com.lhsz.bandian.jt.utils.JtApplyConstants;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.utils.DateUtils;
import com.lhsz.bandian.utils.HttpUtils;
import com.lhsz.bandian.utils.Md5Utils;
import com.lhsz.bandian.utils.PDFUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 申报职称 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class TitleApplyServiceImpl extends ServiceImpl<TitleApplyMapper, TitleApply> implements ITitleApplyService {
    private static final Logger log = LoggerFactory.getLogger(TitleApplyServiceImpl.class);
    private final TitleApplyMapper titleApplyMapper;
    private final TokenService tokenService;
    private final IJuryDeclarePlanService juryDeclarePlanService;
    private final IUserProfileService userProfileService;
    private final IJuryDeptService juryDeptService;
    private final IDeclareTitleService iDeclareTitleService;
    private final IFlowService iFlowService;
    private final IJuryDeclarePlanService iJuryDeclarePlanService;
    private final ITitleCheckService iTitleCheckService;
    private final IJuryDeptUserService iJuryDeptUserService;
    private final IWorkService iWorkService;
    private final IEduService iEduService;
    private final ITrainingEduService iTrainingEduService;
    private final IAssessmentService iAssessmentService;

    @Autowired
    public TitleApplyServiceImpl(TitleApplyMapper titleApplyMapper, TokenService tokenService, IJuryDeclarePlanService juryDeclarePlanService, IUserProfileService userProfileService, IJuryDeptService juryDeptService, IDeclareTitleService iDeclareTitleService, IFlowService iFlowService, IJuryDeclarePlanService iJuryDeclarePlanService, ITitleCheckService iTitleCheckService, IJuryDeptUserService iJuryDeptUserService, IWorkService iWorkService, IEduService iEduService, ITrainingEduService iTrainingEduService, IAssessmentService iAssessmentService) {
        this.titleApplyMapper = titleApplyMapper;
        this.tokenService = tokenService;
        this.juryDeclarePlanService = juryDeclarePlanService;
        this.userProfileService = userProfileService;
        this.juryDeptService = juryDeptService;
        this.iDeclareTitleService = iDeclareTitleService;
        this.iFlowService = iFlowService;
        this.iJuryDeclarePlanService = iJuryDeclarePlanService;
        this.iTitleCheckService = iTitleCheckService;
        this.iJuryDeptUserService = iJuryDeptUserService;
        this.iWorkService = iWorkService;
        this.iEduService = iEduService;
        this.iTrainingEduService = iTrainingEduService;
        this.iAssessmentService = iAssessmentService;
    }
    @Override
    public List<TitleApplyDTO> list(TitleApply titleApply) {
        return titleApplyMapper.selectMapperList(titleApply);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(TitleApplyDTO titleApplyDTO) {
        TitleApply titleApply = new TitleApply();
        BeanUtils.copyProperties(titleApplyDTO,titleApply);
        titleApply.setApplyId(titleApplyDTO.getId());
        updateById(titleApply);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(TitleApplyDTO titleApplyDTO) {
        User loginUserToUser = tokenService.getLoginUserToUser();
        TitleApplyCheckCountDTO titleApplyCheckCountDTO = titleApplyMapper.selectRequiredCount(loginUserToUser.getUserId());
        UserProfileDTO userProfile = userProfileService.selectById(loginUserToUser.getUserId());
        if (userProfile == null){
            throw new NoticeException("未填写用户基本信息");
        } else if(userProfile.getHigherOffice() == null){
            throw new NoticeException("请完善用户基本信息");
        }

        if("0".equals(titleApplyCheckCountDTO.getEduCheckCount())){
            throw new NoticeException("教育经历为必填项");
        }else if("0".equals(titleApplyCheckCountDTO.getWorkCheckCount())){
            throw new NoticeException("工作经历为必填项");
        }else if ("0".equals(titleApplyCheckCountDTO.getAssessmentCheckCount())){
            throw new NoticeException("考核情况为必填项");
        }

        String chkOfficeId;
        String chkOfficeName;

        // 获取评委会id
        JuryDeclarePlan declarePlan = juryDeclarePlanService.getById(titleApplyDTO.getPlanId());
        JuryDeptDTO juryDeptDTO = juryDeptService.selectById(declarePlan.getDeptId());

        // 获取流程值
        JuryDeclarePlanDTO juryDeclarePlanDTO = iJuryDeclarePlanService.selectById(titleApplyDTO.getPlanId());
        DeclareTitle declareTitle = iDeclareTitleService.getFlowIdByCode(juryDeclarePlanDTO.getDeclareTitle());
        FlowDTO flowDTO = iFlowService.selectById(declareTitle.getFlowId());
        String flowValue = flowDTO.getFlowValue();

        TitleApply titleApply = new TitleApply();
        TitleCheck titleCheck = new TitleCheck();

        if(JtApplyConstants.USER_TO_ADMIN.equals(flowValue)){
            titleCheck.setDeptType(JtApplyConstants.ADMIN_DEPT);
            titleApply.setChkStatus(JtApplyConstants.WAIT_ADMIN_DEPT);
            titleCheck.setChkStatus(JtApplyConstants.WAIT_ADMIN_DEPT);
            // 使用主管部门id
            chkOfficeId = userProfile.getHigherOffice();
            JuryDeptDTO juryDeptManagerDTO = juryDeptService.selectById(chkOfficeId);
            chkOfficeName = juryDeptManagerDTO.getDeptName();
        }else if(JtApplyConstants.USER_TO_JURY.equals(flowValue)){
            titleCheck.setDeptType(JtApplyConstants.JURY_DEPT);
            titleApply.setChkStatus(JtApplyConstants.WAIT_JURY);
            titleCheck.setChkStatus(JtApplyConstants.WAIT_JURY);
            // 使用评委会id
            chkOfficeId = declarePlan.getDeptId();
            chkOfficeName = juryDeptDTO.getDeptName();
        }
        else if(JtApplyConstants.USER_TO_ADMIN_THEN_JURY.equals(flowValue)){
            titleCheck.setDeptType(JtApplyConstants.ADMIN_DEPT);
            titleApply.setChkStatus(JtApplyConstants.WAIT_ADMIN_DEPT);
            titleCheck.setChkStatus(JtApplyConstants.WAIT_ADMIN_DEPT);
            // 使用主管部门id
            chkOfficeId = userProfile.getHigherOffice();
            JuryDeptDTO juryDeptManagerDTO = juryDeptService.selectById(chkOfficeId);
            chkOfficeName = juryDeptManagerDTO.getDeptName();
        }else {
            throw new NoticeException("无对应评审流程");
        }

        // 构造主申请记录
        String applyId = UUID.randomUUID().toString();
        titleApply.setApplyId(applyId);
        titleApply.setIsDeleted(0);
        titleApply.setPlanId(declarePlan.getPlanId());
        titleApply.setUserId(loginUserToUser.getUserId());
        titleApply.setApplyYear(declarePlan.getDeclareYear());
        titleApply.setFullName(userProfile.getFullName());
        titleApply.setPhoto(userProfile.getCertPhoto());
        titleApply.setDeclareCategory(declarePlan.getDeclareCategory());
        titleApply.setDeclareSpecialty(declarePlan.getDeclareSpecialty());
        titleApply.setDeclareTitle(declarePlan.getDeclareTitle());
        titleApply.setChkOfficeId(chkOfficeId);
        titleApply.setChkOfficeName(chkOfficeName);
        titleApply.setSubmitTimes(1);

        // 构造评审流程记录
        titleCheck.setApplyId(applyId);
        titleCheck.setChkOfficeId(chkOfficeId);
        titleCheck.setChkOfficeName(chkOfficeName);
        titleCheck.setChkStep(1);
        titleCheck.setIsChecked(false);

        // 更新业绩库状态
        save(titleApply);
        // 添加申报职称审核
        iTitleCheckService.add(titleCheck);

        // 初始化业绩库各记录审核状态为待审核
        ChkDTO chkDTO=new ChkDTO();
        chkDTO.setUserId(loginUserToUser.getUserId());
        chkDTO.setChkStatus(JtApplyConstants.WAIT_AUDIT);
        titleApplyMapper.updateStatus(chkDTO);
    }

    @Override
    public TitleApplyDTO selectById(String id) {
        TitleApply titleApply = titleApplyMapper.selectById(id);
        return new TitleApplyDTO(titleApply);
    }

    @Override
    public TitleApplyDTO selectByUserId(String id) {
        QueryWrapper<TitleApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        TitleApply titleApply = titleApplyMapper.selectOne(queryWrapper);
        return new TitleApplyDTO(titleApply);
    }

    @Override
    public int del(String id) {
        return titleApplyMapper.deleteById(id);
    }

    /**
     *
     * 1 待主管部门审核 => 主管部门审核通过 or 主管部门审核不通过
     * 2 待评委会审核 => 评委会审核通过 or 评委会审核不通过
     * 3 待主管部门审核
     * 			|\
     * 		   /  \
     * 		  /	   \
     * 待评委会审核   主管部门审核不通过
     * 		|   \
     * 	   /     \
     * 	  /	      \
     * 评委会审    评委会审核不通过
     * 核通过
     *
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addCheck(TitleApplyCheckDTO titleApplyCheckDTO) throws Exception {
        User loginUserToUser = tokenService.getLoginUserToUser();

        String chkUserId = loginUserToUser.getUserId();
        JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(chkUserId);
        if(juryDeptUser == null){
            throw new NoticeException("当前用户不属于评委会或主管部门用户");
        }
        // 当前审核人的各项信息
        JuryDeptDTO juryDeptDTO = juryDeptService.selectById(juryDeptUser.getDeptId());
        titleApplyCheckDTO.setChkTime(DateUtils.getNowDate());
        titleApplyCheckDTO.setChkOfficeId(juryDeptUser.getDeptId());
        titleApplyCheckDTO.setChkOfficeName(juryDeptDTO.getDeptName());
        titleApplyCheckDTO.setChkUserId(chkUserId);
        titleApplyCheckDTO.setChkUserName(loginUserToUser.getUserName());

        // 该用户申报计划的流程值
        TitleApplyDTO titleApplyDTO = selectByUserId(titleApplyCheckDTO.getUserId());
        String flowValue = iFlowService.selectFlowValueByUserId(titleApplyCheckDTO.getUserId());

        // 初始化审核记录
        TitleCheck titleCheck = new TitleCheck();
        titleCheck.setApplyId(titleApplyDTO.getApplyId());
        titleCheck.setIsChecked(true);
        titleCheck.setChkUserId(loginUserToUser.getUserId());
        titleCheck.setChkUserName(loginUserToUser.getUserName());
        titleCheck.setChkTime(LocalDateTime.now());
        titleCheck.setChkReason(titleApplyCheckDTO.getAllCheckReason());

        if(JtApplyConstants.USER_TO_ADMIN.equals(flowValue)) {
            setTitleApplyCheckDTO(titleApplyCheckDTO, titleCheck, JtApplyConstants.PASS_ADMIN_DEPT, JtApplyConstants.REJECT_ADMIN_DEPT);
            iTitleCheckService.updateByApplyId(titleCheck);
            titleApplyCheckDTO.setApplyId(titleApplyDTO.getApplyId());
            titleApplyMapper.updateCheck(titleApplyCheckDTO);
            boolean addResult = addEliteDataBase(titleApplyDTO.getUserId());
        }else if (JtApplyConstants.USER_TO_JURY.equals(flowValue)){
            setTitleApplyCheckDTO(titleApplyCheckDTO, titleCheck, JtApplyConstants.PASS_JURY, JtApplyConstants.REJECT_JURY);
            iTitleCheckService.updateByApplyId(titleCheck);
            titleApplyCheckDTO.setApplyId(titleApplyDTO.getApplyId());
            titleApplyMapper.updateCheck(titleApplyCheckDTO);
            boolean addResult = addEliteDataBase(titleApplyDTO.getUserId());
        }else if (JtApplyConstants.USER_TO_ADMIN_THEN_JURY.equals(flowValue)){
            /*
             * step 来源为审核表中这次申请的记录数量
             * step = 1 代表用户 => 主管部门这一步
             * step = 2 代表主管部门 => 评委会这一步
             * */
            int step = iTitleCheckService.countByApplyId(titleApplyDTO.getApplyId());
            if(step == 1){
                setTitleApplyCheckDTO(titleApplyCheckDTO, titleCheck, JtApplyConstants.WAIT_JURY, JtApplyConstants.REJECT_ADMIN_DEPT);
                iTitleCheckService.updateByApplyId(titleCheck);

                // 更新业绩库各项记录的审核相关内容
                titleApplyMapper.updateCheck(titleApplyCheckDTO);

                if(JtApplyConstants.REJECT_ADMIN_DEPT.equals(titleApplyCheckDTO.getMainState())){
                    TitleApply titleApply = new TitleApply();
                    titleApply.setChkStatus(JtApplyConstants.REJECT_ADMIN_DEPT);
                    QueryWrapper<TitleApply> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("apply_id", titleApplyDTO.getApplyId());
                    update(titleApply, queryWrapper);
                }

                if (JtApplyConstants.WAIT_JURY.equals(titleApplyCheckDTO.getMainState())) {
                    // 修改jt_title_apply.chk_office_id 为评委会 状态置为待审核 重置提交次数为1
                    JuryDeclarePlan declarePlan = juryDeclarePlanService.getById(titleApplyDTO.getPlanId());
                    JuryDeptDTO juryDept = juryDeptService.selectById(declarePlan.getDeptId());
                    TitleApply titleApply = new TitleApply();
                    titleApply.setChkOfficeId(juryDept.getDeptId());
                    titleApply.setChkOfficeName(juryDept.getDeptName());
                    titleApply.setSubmitTimes(1);
                    titleApply.setChkStatus(titleApplyCheckDTO.getMainState());
                    QueryWrapper<TitleApply> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("apply_id", titleApplyDTO.getApplyId());
                    update(titleApply, queryWrapper);

                    //向jt_title_check 插入一条评委会审核记录
                    TitleCheck juryCheckRecord = new TitleCheck();
                    String checkId = UUID.randomUUID().toString();
                    juryCheckRecord.setCheckId(checkId);
                    juryCheckRecord.setApplyId(titleApplyDTO.getApplyId());
                    juryCheckRecord.setChkOfficeId(juryDept.getDeptId());
                    juryCheckRecord.setChkOfficeName(juryDept.getDeptName());
                    juryCheckRecord.setDeptType(JtApplyConstants.JURY_DEPT);
                    juryCheckRecord.setChkStatus(JtApplyConstants.WAIT_JURY);
                    juryCheckRecord.setChkStep(2);
                    juryCheckRecord.setIsChecked(false);
                    iTitleCheckService.add(juryCheckRecord);
                }
            }else if(step == 2){
                setTitleApplyCheckDTO(titleApplyCheckDTO, titleCheck, JtApplyConstants.PASS_JURY, JtApplyConstants.REJECT_JURY);
                titleCheck.setDeptType(2);
                iTitleCheckService.updateByApplyId(titleCheck);
                titleApplyCheckDTO.setApplyId(titleApplyDTO.getApplyId());
                titleApplyMapper.updateCheck(titleApplyCheckDTO);
                boolean addResult = addEliteDataBase(titleApplyDTO.getUserId());
            }
        }
    }

    /**
     * 向人才库发送审核通过人员信息
     *
     * @param userId 用户id
     * @return 结果
     */
    private boolean addEliteDataBase(String userId){
        String url = "https://1.71.190.188:14600/api/subsystem/expertTe/addSave";
        //String url = "http://192.168.0.123:8090/api/subsystem/expertTe/addSave";
        // 上报用户ID
        String userIdFk = "2464";
        // hash 过程中用到的key
        String key = "96169";

        HashMap<String, String> paramsMap = new HashMap<>(22);

        UserProfileDetailDTO userProfileDetailDTO = userProfileService.detailById(userId);
        UserProfileDTO data = userProfileDetailDTO.getData();

        TitleApplyDTO titleApplyDTO = selectByUserId(userId);
        JuryDeclarePlan declarePlan = juryDeclarePlanService.getById(titleApplyDTO.getPlanId());
        JuryDeptDTO juryDept = juryDeptService.selectById(declarePlan.getDeptId());

        // 年度
        paramsMap.put("year",titleApplyDTO.getApplyYear());

        // 性别,身份证号,现工作单位,从事专业
        paramsMap.put("name", data.getFullName());
        paramsMap.put("sex", String.valueOf(Integer.parseInt(data.getGender())-1));
        paramsMap.put("idCard", data.getCertNo());
        paramsMap.put("work", data.getWorkPlace());
        paramsMap.put("workEngaged", data.getDeclareSpecialtyName());
        // 上级主管部门,专业技术职务任职资格，专业技术职务任职资格取得时间，专业技术职务任职资格证书编号
        paramsMap.put("department", data.getHigherOfficeName());
        paramsMap.put("posts", data.getDeclareTypeName());
        paramsMap.put("postsDate",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        paramsMap.put("postsNo","无");

        // 评委会名称
        paramsMap.put("committee",juryDept.getDeptName());
        // 专业技术职务任职资格通过文件号
        paramsMap.put("documentNo","无");

        paramsMap.put("phone", data.getMobile());
        paramsMap.put("email","无");
        paramsMap.put("userIdFk",userIdFk);
        paramsMap.put("status","0");

        // 为生成的 hash 前面加4位随机数，后面加6位
        Random rand = new Random();
        int startRandom = rand.nextInt(900)+1000;
        int endRandom = rand.nextInt(900)+100000;

        // 计算签名
        StringBuilder sign = new StringBuilder();
        sign.append(startRandom).append(Md5Utils.hash(LocalDateTime.now().getYear() + data.getCertNo()+
                data.getMobile()+userIdFk+key)).append(endRandom);

        paramsMap.put("sign",sign.toString());

        String resultString = HttpUtils.sendPostJson(url, JSON.toJSONString(paramsMap));
        JSONObject jsonObject = JSON.parseObject(resultString);
        String result = jsonObject.getString("result");
        if(StringUtils.equals("SUCCESS",result)){
            log.info(result);
            return true;
        }else if(StringUtils.equals("ERROR",result)){
            log.error(jsonObject.getString("message"));
            return false;
        }
        return false;
    }

    /**
     * 设置主申请状态和审核状态
     * @param titleApplyCheckDTO 职称申请审核对象
     * @param titleCheck 申报职称审核对象
     * @param trueString 子项目全部通过时为主状态赋的值
     * @param falseString 子项目有不通过或者退回时为主状态赋的值
     * @throws Exception 反射相关异常
     */
    private void setTitleApplyCheckDTO(TitleApplyCheckDTO titleApplyCheckDTO, TitleCheck titleCheck, String trueString, String falseString) throws Exception {
        boolean mainState = isAllFieldNull(titleApplyCheckDTO);
        if (mainState) {
            titleApplyCheckDTO.setMainState(trueString);
            titleCheck.setChkStatus(trueString);
        } else {
            titleApplyCheckDTO.setMainState(falseString);
            titleCheck.setChkStatus(falseString);
        }
        titleApplyCheckDTO.setResetFlag("0");
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addResetCheck(TitleApplyCheckDTO titleApplyCheckDTO) {
        TitleApplyDTO titleApplyDTO = selectByUserId(titleApplyCheckDTO.getUserId());
        // 将对应单位的不通过改为待审核
        if(JtApplyConstants.REJECT_ADMIN_DEPT.equals(titleApplyDTO.getChkStatus())){
            titleApplyCheckDTO.setMainState(JtApplyConstants.WAIT_ADMIN_DEPT);
        }else if (JtApplyConstants.REJECT_JURY.equals(titleApplyDTO.getChkStatus())){
            titleApplyCheckDTO.setMainState(JtApplyConstants.WAIT_JURY);
        }
        // 过滤重复提交
        if(JtApplyConstants.WAIT_ADMIN_DEPT.equals(titleApplyDTO.getChkStatus()) || JtApplyConstants.WAIT_JURY.equals(titleApplyDTO.getChkStatus())){
            throw new NoticeException("已进入主管部门或评委会审核列表，请勿重复提交");
        }

        titleApplyCheckDTO.setResetFlag("1");
        titleApplyMapper.updateCheck(titleApplyCheckDTO);
        if(BandianProperties.limitSubmit != 0){
            // 判断限制次数 limitSubmit是限制总的提交次数
            TitleApplyDTO applyDTO = selectByUserId(titleApplyCheckDTO.getUserId());
            if(BandianProperties.limitSubmit <= applyDTO.getSubmitTimes()){
                throw new NoticeException("已到达提交次数上限");
            }
        }

        // 重新提交后主记录提交次数加一
        titleApplyMapper.updateSubmitTimes(titleApplyCheckDTO.getUserId());
    }


    /**
     * 判断该对象的一部分属性是否为 "退回" 或者 "不通过"
     * @param obj 对象
     * @return boolean
     * @throws Exception 反射相关异常
     */
    public static boolean isAllFieldNull(Object obj) throws Exception{
        // 取到obj的class, 并取到所有属性
        Field[] fs = obj.getClass().getDeclaredFields();
        boolean flag = true;
        // 遍历所有属性
        for (Field f : fs) {
            // 设置私有属性也是可以访问的
            f.setAccessible(true);
            // 属性中存在值等于02或者03的情况返回false
            if("02".equals(f.get(obj)) || "03".equals(f.get(obj)) ) {
                // 有属性满足3个条件的话, 那么说明对象属性不全为空
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 生成职称评审表
     * @param userId 用户id
     * @return 文件路径
     * */
    @Override
    public String getPDF(String userId) throws IOException, DocumentException {
        // 初始值大小为 = (需要存储的元素个数/负载因子)+1
        Map<String,String> map = new HashMap<>(34);
        // 第一页
        // 基本信息
        UserProfileDetailDTO userProfileDetailDTO = userProfileService.detailById(userId);
        map.put("name",userProfileDetailDTO.getData().getFullName());
        map.put("sex", userProfileDetailDTO.getData().getGenderName());
        map.put("birthday",userProfileDetailDTO.getData().getBirthday());
        map.put("work",userProfileDetailDTO.getData().getWorkPlace());
        map.put("professional",userProfileDetailDTO.getData().getDeclareCategoryName());
        map.put("idCard",userProfileDetailDTO.getData().getCertNo());
        map.put("workType",userProfileDetailDTO.getData().getWorkCorpNatureName());
        map.put("workDepartment",userProfileDetailDTO.getData().getWorkDept());
        map.put("duties",userProfileDetailDTO.getData().getJobTitle());
        map.put("storage",userProfileDetailDTO.getData().getFilePosition());
        map.put("type",userProfileDetailDTO.getData().getDeclareTypeName());
        map.put("department",userProfileDetailDTO.getData().getHigherOfficeName());
        map.put("phone",userProfileDetailDTO.getData().getMobile());
        // 工作经历
        Work works = new Work();
        works.setUserId(userId);
        List<WorkDTO> workDTOList = iWorkService.list(works);
        for (int i = 0;i<workDTOList.size();i++) {
            String startToEndTime = workDTOList.get(i).getBeginDate() +
                    "至" +
                    workDTOList.get(i).getEndDate();
            map.put("s_e_time"+i, startToEndTime);
            map.put("main_work"+i,workDTOList.get(i).getWorkCorp());
            map.put("main_job"+i,workDTOList.get(i).getJobTitle());
            map.put("main_position"+i,workDTOList.get(i).getJobDescription());
        }
        // 教育信息
        Edu edus = new Edu();
        edus.setUserId(userId);
        List<JtEduDTO> jtEduDTOS = iEduService.listEdu(edus);
        if(jtEduDTOS.size() >= 1) {
            map.put("first_degree", jtEduDTOS.get(0).getEduDegreeName());
            map.put("first_school", jtEduDTOS.get(0).getGradSchool());
            map.put("first_professional", jtEduDTOS.get(0).getSpecialty());
            map.put("first_schoolDate", jtEduDTOS.get(0).getGradTime());
            map.put("first_degreeIn", jtEduDTOS.get(0).getEduDegreeName());
        }
        if(jtEduDTOS.size() >= 2) {
            map.put("other_degree", jtEduDTOS.get(1).getDegreeName());
            map.put("other_school", jtEduDTOS.get(1).getGradSchool());
            map.put("other_professional", jtEduDTOS.get(1).getSpecialty());
            map.put("other_schoolDate", jtEduDTOS.get(1).getGradTime());
            map.put("other_degreeIn", jtEduDTOS.get(1).getEduDegreeName());
        }
        if(jtEduDTOS.size() >= 3) {
            map.put("top_degree", jtEduDTOS.get(2).getEduDegreeName());
            map.put("top_school", jtEduDTOS.get(2).getGradSchool());
            map.put("top_professional", jtEduDTOS.get(2).getSpecialty());
            map.put("top_schoolDate", jtEduDTOS.get(2).getGradTime());
            map.put("top_degreeIn", jtEduDTOS.get(2).getEduDegreeName());
        }
        // 继续教育
        TrainingEdu trainingEdu = new TrainingEdu();
        trainingEdu.setUserId(userId);
        List<TrainingEduDTO> trainingEduList = iTrainingEduService.list(trainingEdu);
        for (int i = 0;i<trainingEduList.size();i++) {
            String startToEndTime = trainingEduList.get(i).getBeginDate() +
                    "至" +
                    trainingEduList.get(i).getEndDate();
            map.put("pxdate"+i, startToEndTime);
            map.put("pxwork"+i,trainingEduList.get(i).getTrainingSchool());
            map.put("pxcon"+i,trainingEduList.get(i).getLearnDescription());
        }
        // 考核情况
        QueryWrapper<Assessment> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", userId);
        List<Assessment> assessmentList = iAssessmentService.list(queryWrapper2);
        int goodCount = 0;
        int acceptsCount = 0;
        for (Assessment assessment : assessmentList) {
            if("01".equals(assessment.getAssessmentResult())){
                goodCount++;
            }
            if("02".equals(assessment.getAssessmentResult())){
                acceptsCount++;
            }
        }
        map.put("goodCount", String.valueOf(goodCount));
        map.put("acceptsCount", String.valueOf(acceptsCount));

        ByteArrayOutputStream pdf = null;
        FileOutputStream out = null;
        String newPath;
        try {
            //重命名pdf
            newPath = userId+".pdf";
            //新的文件路径
            String newFilePath = BandianProperties.uploadPath +"/" + BandianProperties.appraisePath +"/" + newPath;
            // log.info("newFilePath:"+newFilePath);
            //填充模板
            pdf = PDFUtils.createPdfStream("/PDFtemplates", "appraiseInfo.pdf", map);

            out = new FileOutputStream(newFilePath);
            out.write(pdf.toByteArray());
        } finally {
            if(pdf!=null){
                try {
                    pdf.close();
                }catch (Exception e){
                    log.error("pdf生成失败", e);
                }
            }
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                }catch (Exception e){
                    log.error("文件输出失败", e);
                }
            }
        }
        return newPath;
    }

    @Override
    public String getCertificate(String userId) throws IOException, DocumentException {
        // 初始值大小为 = (需要存储的元素个数/负载因子)+1
        Map<String,String> map = new HashMap<>(34);
        // 第一页
        // 基本信息
        UserProfileDetailDTO userProfileDetailDTO = userProfileService.detailById(userId);
        TitleApplyDTO titleApplyDTO = selectByUserId(userId);
        JuryDeclarePlan declarePlan = juryDeclarePlanService.getById(titleApplyDTO.getPlanId());
        JuryDeptDTO juryDept = juryDeptService.selectById(declarePlan.getDeptId());

        map.put("name",userProfileDetailDTO.getData().getFullName());
        map.put("gender", userProfileDetailDTO.getData().getGenderName());
        map.put("certNo",userProfileDetailDTO.getData().getCertNo());
        map.put("workPlace",userProfileDetailDTO.getData().getWorkPlace());
        map.put("declareTitle",userProfileDetailDTO.getData().getDeclareTitleName());
        map.put("declareType",userProfileDetailDTO.getData().getDeclareSpecialtyName());
        map.put("certificateNo","0000000001");
        map.put("passTime",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        map.put("passDept",juryDept.getDeptName());
        map.put("issueDate",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        ByteArrayOutputStream pdf = null;
        FileOutputStream out = null;
        String newPath;
        try {
            //重命名pdf
            newPath = "certificate_"+userId+".pdf";
            //新的文件路径
            String newFilePath = BandianProperties.uploadPath +"/" + BandianProperties.appraisePath +"/" + newPath;
            // log.info("newFilePath:"+newFilePath);
            //填充模板
            pdf = PDFUtils.createPdfStream("/PDFtemplates", "skill_certificate_template.pdf",
                    map,BandianProperties.uploadPath+userProfileDetailDTO.getData().getCertPhoto());

            out = new FileOutputStream(newFilePath);
            out.write(pdf.toByteArray());
        } finally {
            if(pdf!=null){
                try {
                    pdf.close();
                }catch (Exception e){
                    log.error("pdf流关闭失败", e);
                }
            }
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                }catch (Exception e){
                    log.error("文件输出流关闭失败", e);
                }
            }
        }
        return newPath;
    }

    /**
     * 获取业绩库各表记录数量
     * */
    @Override
    public TitleApplyCheckCountDTO getCheckCount(String userId) {
        return titleApplyMapper.selectJtCount(userId);
    }

    /**
     * 获取待审核、不通过和通过数量
     */
    @Override
    public TitleApplyCountDTO getStateCount(TitleApply titleApply){
        return titleApplyMapper.selectStateCount(titleApply);
    }

    /**
     * 使用 userId 获得业绩库状态信息统计
     * @param userId 用户Id
     * @return 总的待审核、退回、不通过和通过数量
     */
    @Override
    public HashMap<String, Integer> getJtTotalStateCount(String userId) {
        TitleApplyJtStateCountDTO stateCount = titleApplyMapper.selectJtStateCount(userId);
        int totalWait = stateCount.getEduWaitCount()+stateCount.getTrainingEduWaitCount()+
                stateCount.getWorkWaitCount()+stateCount.getAwardsWaitCount()+
                stateCount.getHonorsWaitCount()+stateCount.getIncentiveWaitCount()+
                stateCount.getQaCertWaitCount()+stateCount.getThesisWaitCount()+
                stateCount.getAcadTechWaitCount()+stateCount.getEngnTechProjWaitCount()+
                stateCount.getMakeStandardWaitCount()+stateCount.getResearchProjWaitCount()+
                stateCount.getResultUsedWaitCount()+stateCount.getPatentWaitCount()+
                stateCount.getWritingsWaitCount()+stateCount.getChildTeachOpenWaitCount()+
                stateCount.getChildTeachWorkWaitCount()+stateCount.getPartyTeachTeamWorkWaitCount()+
                stateCount.getPartyTeachWorkWaitCount()+stateCount.getSportCoachesWaitCount()+
                stateCount.getSportGameWaitCount()+stateCount.getAssessmentWaitCount()+
                stateCount.getUserProfileWaitCount();
        int totalBack = stateCount.getEduBackCount()+stateCount.getTrainingEduBackCount()+
                stateCount.getWorkBackCount()+stateCount.getAwardsBackCount()+
                stateCount.getHonorsBackCount()+stateCount.getIncentiveBackCount()+
                stateCount.getQaCertBackCount()+stateCount.getThesisBackCount()+
                stateCount.getAcadTechBackCount()+stateCount.getEngnTechProjBackCount()+
                stateCount.getMakeStandardBackCount()+stateCount.getResearchProjBackCount()+
                stateCount.getResultUsedBackCount()+stateCount.getPatentBackCount()+
                stateCount.getWritingsBackCount()+stateCount.getChildTeachOpenBackCount()+
                stateCount.getChildTeachWorkBackCount()+stateCount.getPartyTeachTeamWorkBackCount()+
                stateCount.getPartyTeachWorkBackCount()+stateCount.getSportCoachesBackCount()+
                stateCount.getSportGameBackCount()+stateCount.getAssessmentBackCount()+
                stateCount.getUserProfileBackCount();
        int totalReject = stateCount.getEduRejectCount()+stateCount.getTrainingEduRejectCount()+
                stateCount.getWorkRejectCount()+stateCount.getAwardsRejectCount()+
                stateCount.getHonorsRejectCount()+stateCount.getIncentiveRejectCount()+
                stateCount.getQaCertRejectCount()+stateCount.getThesisRejectCount()+
                stateCount.getAcadTechRejectCount()+stateCount.getEngnTechProjRejectCount()+
                stateCount.getMakeStandardRejectCount()+stateCount.getResearchProjRejectCount()+
                stateCount.getResultUsedRejectCount()+stateCount.getPatentRejectCount()+
                stateCount.getWritingsRejectCount()+stateCount.getChildTeachOpenRejectCount()+
                stateCount.getChildTeachWorkRejectCount()+stateCount.getPartyTeachTeamWorkRejectCount()+
                stateCount.getPartyTeachWorkRejectCount()+stateCount.getSportCoachesRejectCount()+
                stateCount.getSportGameRejectCount()+stateCount.getAssessmentRejectCount()+
                stateCount.getUserProfileRejectCount();
        int totalPass = stateCount.getEduPassCount()+stateCount.getTrainingEduPassCount()+
                stateCount.getWorkPassCount()+stateCount.getAwardsPassCount()+
                stateCount.getHonorsPassCount()+stateCount.getIncentivePassCount()+
                stateCount.getQaCertPassCount()+stateCount.getThesisPassCount()+
                stateCount.getAcadTechPassCount()+stateCount.getEngnTechProjPassCount()+
                stateCount.getMakeStandardPassCount()+stateCount.getResearchProjPassCount()+
                stateCount.getResultUsedPassCount()+stateCount.getPatentPassCount()+
                stateCount.getWritingsPassCount()+stateCount.getChildTeachOpenPassCount()+
                stateCount.getChildTeachWorkPassCount()+stateCount.getPartyTeachTeamWorkPassCount()+
                stateCount.getPartyTeachWorkPassCount()+stateCount.getSportCoachesPassCount()+
                stateCount.getSportGamePassCount()+stateCount.getAssessmentPassCount()+
                stateCount.getUserProfilePassCount();

        HashMap<String, Integer> countResult = new HashMap<>(7);
        countResult.put("totalCount", totalWait + totalBack + totalReject + totalPass);
        countResult.put("totalWait", totalWait);
        countResult.put("totalBack", totalBack);
        countResult.put("totalReject", totalReject);
        countResult.put("totalPass", totalPass);

        return countResult;
    }

    @Override
    public TitleApplyJtStateCountDTO getJtStateCount(String userId) {
        return titleApplyMapper.selectJtStateCount(userId);
    }

    @Override
    public List<ApplyCount> getTotalStateChart() {
        User user= tokenService.getLoginUserToUser();
        String chkOfficeId = null;
        if(user.getUserType() == 3){
            JuryDeptUser juryDeptUser = iJuryDeptUserService.selectByUserId(user.getUserId());
            chkOfficeId = juryDeptUser.getDeptId();
        }

        List<TitleApplyChart> titleApplyCharts = titleApplyMapper.selectApplyChart(chkOfficeId);

        Set<String> betweenDate = DateUtils.betweenDate(DateUtils.dateTime(DateUtils.dayAddNum(DateUtils.getNowDate(),-6)),DateUtils.getDate());

        // 将查询到的数据放入对应数据集
        LinkedList<ApplyCount> list = new LinkedList<>();
        for (String dateString:betweenDate) {
            int newCountTemp = 0;
            int waitCountTemp = 0;
            int passCountTemp = 0;
            int noPassCountTemp = 0;
            for (TitleApplyChart titleApplyChart:titleApplyCharts) {
                if(StringUtils.equals(titleApplyChart.getDays(),dateString)) {
                    newCountTemp = titleApplyChart.getCount() + newCountTemp;
                }
                if(StringUtils.equals(titleApplyChart.getDays(),dateString) &&
                        (StringUtils.equals(titleApplyChart.getStatus(),"01") ||
                                StringUtils.equals(titleApplyChart.getStatus(),"04"))){
                    waitCountTemp = titleApplyChart.getCount() + waitCountTemp;
                }
                if(StringUtils.equals(titleApplyChart.getDays(),dateString) &&
                        (StringUtils.equals(titleApplyChart.getStatus(),"02") ||
                                StringUtils.equals(titleApplyChart.getStatus(),"05"))){
                    passCountTemp = titleApplyChart.getCount() + passCountTemp;
                }
                if(StringUtils.equals(titleApplyChart.getDays(),dateString) &&
                        (StringUtils.equals(titleApplyChart.getStatus(),"03") ||
                                StringUtils.equals(titleApplyChart.getStatus(),"06"))){
                    noPassCountTemp = titleApplyChart.getCount() + noPassCountTemp;
                }
            }
            ApplyCount newApplyCountList = new ApplyCount("newApplyCount",dateString,newCountTemp);
            list.add(newApplyCountList);
            ApplyCount applyCountList = new ApplyCount("waitAuditCount",dateString,waitCountTemp);
            list.add(applyCountList);
            ApplyCount passCountList = new ApplyCount("passCount",dateString,passCountTemp);
            list.add(passCountList);
            ApplyCount noPassCountList = new ApplyCount("noPassCount",dateString,noPassCountTemp);
            list.add(noPassCountList);
        }
        return list;
    }
}
