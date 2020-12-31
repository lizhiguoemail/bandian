package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.AwardsAddDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsDetailDTO;
import com.lhsz.bandian.jt.DTO.request.AwardsUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.JtAcadTechDTO;
import com.lhsz.bandian.jt.DTO.response.JtAwardsDTO;
import com.lhsz.bandian.jt.entity.Awards;
import com.lhsz.bandian.jt.mapper.AwardsMapper;
import com.lhsz.bandian.jt.service.IAwardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 获奖情况 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class AwardsServiceImpl extends ServiceImpl<AwardsMapper, Awards> implements IAwardsService {

    @Autowired
    private AwardsMapper awardsMapper;

    @Autowired
    private IDictDataService iDictDataService;

    @Autowired
    private IAttachService attachService;

    @Override
    public List<JtAwardsDTO> listAwards(Awards awards) {
        List<JtAwardsDTO> jtAwardsDTOS = awardsMapper.selectMapperList(awards);
        return jtAwardsDTOS;
    }

    @Override
    public JtAwardsDTO getAwards(String id) {
        Awards awards = awardsMapper.selectById(id);
        JtAwardsDTO jtAwardsDTO = new JtAwardsDTO();
        BeanUtils.copyProperties(awards,jtAwardsDTO);
        jtAwardsDTO.setId(id);
        return jtAwardsDTO;
    }

    @Override
    public AwardsDetailDTO detailById(String id) {
        AwardsDetailDTO detailDTO = new AwardsDetailDTO();
        JtAwardsDTO awards =awardsMapper.selectDTO(id);
        detailDTO.setData(awards);
        List<FileUploadDTO> fileUploadDTOS = this.attachService.listByObjectId(awards.getAwardsId());
        detailDTO.setFiles(fileUploadDTOS);
        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void addAwards(JtAwardsDTO jtAwardsDTO) {
        Awards awards = new Awards();
        BeanUtils.copyProperties(jtAwardsDTO,awards);
        jtAwardsDTO.setId(awards.getAwardsId());
        save(awards);
    }

    @Override
    public void addAwards(AwardsAddDTO jtAwardsDTO) {
        String Id = UUID.randomUUID().toString();
        jtAwardsDTO.getData().setAwardsId(Id);
        save(jtAwardsDTO.getData());
        if (jtAwardsDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtAwardsDTO.getFiles().forEach(obj ->{
                obj.setObjectId(Id);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateAwards(JtAwardsDTO jtAwardsDTO) {
        Awards awards = new Awards();
        BeanUtils.copyProperties(jtAwardsDTO,awards);
        awards.setAwardsId(jtAwardsDTO.getId());
        updateById(awards);
    }

    @Override
    public void updateAwards(AwardsUpdateDTO jtAwardsDTO) {
        updateById(jtAwardsDTO.getData());
        if (jtAwardsDTO.getFiles().size() > 0 ){
            List<Attach> attachs = new ArrayList<>();
            jtAwardsDTO.getFiles().forEach(obj ->{
                obj.setObjectId(jtAwardsDTO.getData().getAwardsId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }

    }

    @Override
    public int delAwards(String id) {
        int deleteById = awardsMapper.deleteById(id);
        return deleteById;
    }
}
